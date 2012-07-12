/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.app.client;

import net.sf.mmm.app.shared.GreetingService;
import net.sf.mmm.service.api.client.RemoteInvocationServiceCallback;
import net.sf.mmm.service.api.client.RemoteInvocationServiceCaller;
import net.sf.mmm.service.api.client.RemoteInvocationServiceQueue;
import net.sf.mmm.ui.toolkit.api.feature.UiFeatureClick;
import net.sf.mmm.ui.toolkit.api.handler.event.UiHandlerEventClick;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetFactory;
import net.sf.mmm.ui.toolkit.api.widget.atomic.UiWidgetButton;
import net.sf.mmm.ui.toolkit.api.widget.atomic.UiWidgetImage;
import net.sf.mmm.ui.toolkit.api.widget.atomic.UiWidgetTextField;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.UiWidgetFactoryGwt;
import net.sf.mmm.util.filter.api.CharFilter;
import net.sf.mmm.util.nls.api.NlsNullPointerException;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Mmm implements EntryPoint {

  /**
   * The message displayed to the user when the server cannot be reached or returns an error.
   */
  private static final String SERVER_ERROR = "An error occurred while "
      + "attempting to contact the server. Please check your network " + "connection and try again.";

  /**
   * Create a remote service proxy to talk to the server-side Greeting service.
   */
  private final RemoteInvocationServiceCaller serviceCaller = GWT.create(RemoteInvocationServiceCaller.class);

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {

    Log.debug("Loaded");
    UiWidgetFactory<Widget> factory = new UiWidgetFactoryGwt();
    UiWidgetImage image = factory.create(UiWidgetImage.class);
    image.setUrl("http://m-m-m.sourceforge.net/maven/images/logo.png");
    UiWidgetButton button = factory.create(UiWidgetButton.class);
    button.setLabel("Send");
    button.setImage(image);
    button.setTooltip("Send to server");

    UiWidgetTextField textField = factory.create(UiWidgetTextField.class);
    textField.setKeyboardFilter(CharFilter.LATIN_DIGIT_FILTER);

    final Button sendButton = (Button) factory.getNativeWidget(button); // new Button("Send");

    final TextBox nameField = (TextBox) factory.getNativeWidget(textField);
    // nameField.setText("GWT User");
    final Label errorLabel = new Label();

    // We can add style names to widgets
    button.setStyles("sendButton");

    String message = new NlsNullPointerException("test").getMessage();
    // NlsMessage message = NlsAccess.getFactory().create("Hello World {arg}!", "arg", "foo");
    nameField.setValue(message);

    // Add the nameField and sendButton to the RootPanel
    // Use RootPanel.get() to get the entire body element
    RootPanel.get("nameFieldContainer").add(nameField);
    RootPanel.get("sendButtonContainer").add(sendButton);
    RootPanel.get("errorLabelContainer").add(errorLabel);

    // Focus the cursor on the name field when the app loads
    nameField.setFocus(true);
    nameField.selectAll();

    // Create the popup dialog box
    final DialogBox dialogBox = new DialogBox();
    dialogBox.setText("Remote Procedure Call");
    dialogBox.setAnimationEnabled(true);
    final Button closeButton = new Button("Close");
    // We can set the id of a widget by accessing its Element
    closeButton.getElement().setId("closeButton");
    final Label textToServerLabel = new Label();
    final HTML serverResponseLabel = new HTML();
    VerticalPanel dialogVPanel = new VerticalPanel();
    dialogVPanel.addStyleName("dialogVPanel");
    dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
    dialogVPanel.add(textToServerLabel);
    dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
    dialogVPanel.add(serverResponseLabel);
    dialogVPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
    dialogVPanel.add(closeButton);
    dialogBox.setWidget(dialogVPanel);

    // Add a handler to close the DialogBox
    closeButton.addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {

        dialogBox.hide();
        sendButton.setEnabled(true);
        sendButton.setFocus(true);
      }
    });

    // Create a handler for the sendButton and nameField
    class MyHandler implements UiHandlerEventClick, KeyUpHandler {

      /**
       * {@inheritDoc}
       */
      @Override
      public void onClick(UiFeatureClick source, boolean programmatic) {

        sendNameToServer();
      }

      /**
       * Fired when the user types in the nameField.
       */
      public void onKeyUp(KeyUpEvent event) {

        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
          sendNameToServer();
        }
      }

      /**
       * Send the name from the nameField to the server and wait for a response.
       */
      private void sendNameToServer() {

        // First, we validate the input.
        errorLabel.setText("");
        String textToServer = nameField.getText();

        // Then, we send the input to the server.
        sendButton.setEnabled(false);
        textToServerLabel.setText(textToServer);
        serverResponseLabel.setText("");
        RemoteInvocationServiceCallback<String> callback = new RemoteInvocationServiceCallback<String>() {

          /**
           * {@inheritDoc}
           */
          public void onFailure(Throwable failure, boolean complete) {

            // Show the RPC error message to the user
            dialogBox.setText("Remote Procedure Call - Failure");
            serverResponseLabel.addStyleName("serverResponseLabelError");
            serverResponseLabel.setHTML(SERVER_ERROR);
            dialogBox.center();
            closeButton.setFocus(true);
          }

          /**
           * {@inheritDoc}
           */
          public void onSuccess(String result, boolean complete) {

            dialogBox.setText("Remote Procedure Call");
            serverResponseLabel.removeStyleName("serverResponseLabelError");
            serverResponseLabel.setHTML(result);
            dialogBox.center();
            closeButton.setFocus(true);
          }
        };
        RemoteInvocationServiceQueue queue = Mmm.this.serviceCaller.newQueue();
        queue.getServiceClient(GreetingService.class, String.class, callback).greeting(textToServer);
        queue.commit();
      }
    }

    // Add a handler to send the name to the server
    MyHandler handler = new MyHandler();
    button.addClickHandler(handler);
    nameField.addKeyUpHandler(handler);
  }
}
