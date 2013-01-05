/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl.rebind;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.nls.api.NlsBundle;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.ObjectMismatchException;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * This is the abstract base implementation of a GWT {@link Generator} for rebinding the
 * {@link net.sf.mmm.util.nls.api.NlsBundleFactory} implementation.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractNlsBundleGenerator extends Generator {

  /** The suffix for the generated resource-bundle class. */
  private static final String SUFFIX_CLASS = "_GwtImpl";

  /** @see #generateMethodMessageBlock(SourceWriter, TreeLogger, GeneratorContext, JMethod) */
  protected static final String VARIABLE_MESSAGE = "nlsL10nMessage";

  /** @see #generateMethodMessageBlock(SourceWriter, TreeLogger, GeneratorContext, JMethod) */
  protected static final String VARIABLE_ARGUMENTS = "nlsArguments";

  /**
   * The constructor.
   */
  public AbstractNlsBundleGenerator() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {

    TypeOracle typeOracle = context.getTypeOracle();
    JClassType bundleClass = typeOracle.findType(typeName);

    String packageName = bundleClass.getPackage().getName();
    String simpleName = bundleClass.getName() + SUFFIX_CLASS;
    logger.log(TreeLogger.INFO, getClass().getSimpleName() + ": Generating " + simpleName);
    ClassSourceFileComposerFactory sourceComposerFactory = new ClassSourceFileComposerFactory(packageName, simpleName);

    sourceComposerFactory.addImplementedInterface(bundleClass.getQualifiedSourceName());

    generateImports(sourceComposerFactory);

    PrintWriter writer = context.tryCreate(logger, packageName, simpleName);
    if (writer != null) {
      SourceWriter sourceWriter = sourceComposerFactory.createSourceWriter(context, writer);

      generateFields(sourceWriter, logger, context, bundleClass);

      // generate methods of bundle
      for (JMethod method : bundleClass.getOverridableMethods()) {
        JType returnType = method.getReturnType();
        if (NlsMessage.class.getName().equals(returnType.getQualifiedSourceName())) {
          generateMethod(sourceWriter, logger, context, method);
        } else {
          throw new ObjectMismatchException(returnType.getSimpleSourceName(), NlsMessage.class,
              bundleClass.getQualifiedSourceName(), method.getName());
        }
      }
      sourceWriter.commit(logger);
    }
    return sourceComposerFactory.getCreatedClassName();
  }

  /**
   * Generates an implementation of an {@link NlsBundle}-method.
   * 
   * @param sourceWriter is the {@link SourceWriter}.
   * @param logger is the {@link TreeLogger}.
   * @param context is the {@link GeneratorContext}.
   * @param method is the {@link NlsBundle}-method to generate an implementation for.
   */
  protected void generateMethod(SourceWriter sourceWriter, TreeLogger logger, GeneratorContext context, JMethod method) {

    // method signature
    sourceWriter.print("public ");
    sourceWriter.print(NlsMessage.class.getSimpleName());
    sourceWriter.print(" ");
    sourceWriter.print(method.getName());
    sourceWriter.print("(");
    boolean firstParameter = true;
    for (JParameter parameter : method.getParameters()) {
      if (firstParameter) {
        firstParameter = false;
      } else {
        sourceWriter.print(", ");
      }
      sourceWriter.print(parameter.getType().getQualifiedSourceName());
      sourceWriter.print(" ");
      sourceWriter.print(parameter.getName());
    }
    sourceWriter.println(") {");
    sourceWriter.indent();

    // method body...
    generateMethodBody(sourceWriter, logger, context, method);

    // end method
    sourceWriter.outdent();
    sourceWriter.println("}");
    sourceWriter.println();
  }

  /**
   * Generates an the body of an {@link NlsBundle}-method.
   * 
   * @param sourceWriter is the {@link SourceWriter}.
   * @param logger is the {@link TreeLogger}.
   * @param context is the {@link GeneratorContext}.
   * @param method is the {@link NlsBundle}-method to generate an implementation for.
   */
  protected void generateMethodBody(SourceWriter sourceWriter, TreeLogger logger, GeneratorContext context,
      JMethod method) {

    sourceWriter.print("Map<String, Object> ");
    sourceWriter.print(VARIABLE_ARGUMENTS);
    sourceWriter.println(" = new HashMap<String, Object>();");

    // loop over parameters and generate code that puts the parameters into the arguments map
    for (JParameter parameter : method.getParameters()) {
      String name;
      Named namedAnnotation = parameter.getAnnotation(Named.class);
      if (namedAnnotation == null) {
        name = parameter.getName();
      } else {
        name = namedAnnotation.value();
      }
      sourceWriter.print(VARIABLE_ARGUMENTS);
      sourceWriter.print(".put(\"");
      sourceWriter.print(escape(name));
      sourceWriter.print("\", ");
      sourceWriter.print(parameter.getName());
      sourceWriter.println(");");
    }

    generateMethodMessageBlock(sourceWriter, logger, context, method);

    // return NlsAccess.getFactory().create(message, arguments);
    sourceWriter.print("return ");
    sourceWriter.print(NlsAccess.class.getSimpleName());
    sourceWriter.print(".getFactory().create(");
    sourceWriter.print(VARIABLE_MESSAGE);
    sourceWriter.print(", ");
    sourceWriter.print(VARIABLE_ARGUMENTS);
    sourceWriter.println(");");
  }

  /**
   * Generates the block of the {@link NlsBundle}-method body that creates the code block with the
   * {@link String} variable {@link #VARIABLE_MESSAGE}.
   * 
   * @param sourceWriter is the {@link SourceWriter}.
   * @param logger is the {@link TreeLogger}.
   * @param context is the {@link GeneratorContext}.
   * @param method is the {@link NlsBundle}-method to generate an implementation for.
   */
  protected abstract void generateMethodMessageBlock(SourceWriter sourceWriter, TreeLogger logger,
      GeneratorContext context, JMethod method);

  /**
   * Generates the (private) fields.
   * 
   * @param sourceWriter is the {@link SourceWriter}.
   * @param logger is the {@link TreeLogger}.
   * @param context is the {@link GeneratorContext}.
   * @param bundleClass is the {@link JClassType} reflecting the {@link NlsBundle}.
   */
  protected void generateFields(SourceWriter sourceWriter, TreeLogger logger, GeneratorContext context,
      JClassType bundleClass) {

    // nothing by default

  }

  /**
   * Generates the import statements.
   * 
   * @param sourceComposerFactory is the {@link ClassSourceFileComposerFactory}.
   */
  protected void generateImports(ClassSourceFileComposerFactory sourceComposerFactory) {

    sourceComposerFactory.addImport(Map.class.getName());
    sourceComposerFactory.addImport(HashMap.class.getName());
    sourceComposerFactory.addImport(NlsBundle.class.getName());
    sourceComposerFactory.addImport(NlsMessage.class.getName());
    sourceComposerFactory.addImport(NlsAccess.class.getName());
  }
}
