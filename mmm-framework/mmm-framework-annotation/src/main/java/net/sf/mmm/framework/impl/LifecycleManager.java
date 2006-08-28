/* $Id$ */
package net.sf.mmm.framework.impl;

import java.util.concurrent.Executor;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;

import net.sf.mmm.framework.api.ExtendedComponentInstanceContainerIF;
import net.sf.mmm.framework.api.LifecycleManagerIF;
import net.sf.mmm.framework.api.LifecycleMethod;
import net.sf.mmm.framework.base.AbstractLifecycleManager;
import net.sf.mmm.util.reflect.ReflectionUtil;

/**
 * This is the default implementation of the {@link LifecycleManagerIF}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Resource(shareable = true)
public class LifecycleManager extends AbstractLifecycleManager {

    /** the thread pool */
    private Executor threadPool;

    /**
     * The constructor.
     */
    public LifecycleManager() {

        super();
        registerLifecyclePhase(LifecycleMethod.LIFECYCLE_INITIALIZE);
        registerLifecyclePhase(LifecycleMethod.LIFECYCLE_START);
        registerLifecyclePhase(LifecycleMethod.LIFECYCLE_EXECUTE);
        registerLifecyclePhase(LifecycleMethod.LIFECYCLE_STOP);
        registerLifecyclePhase(LifecycleMethod.LIFECYCLE_DISPOSE);
    }

    /**
     * This method gets the thread-pool used to execute lifecycle methods in
     * separate threads.
     * 
     * @return the thread-pool.
     */
    public Executor getThreadPool() {

        return this.threadPool;
    }

    /**
     * This method sets the {@link #getThreadPool() thread-pool}.
     * 
     * @param myThreadPool
     *        is the thread-pool to set.
     */
    @Resource
    public void setThreadPool(Executor myThreadPool) {

        this.threadPool = myThreadPool;
    }

    /**
     * @see net.sf.mmm.framework.api.LifecycleManagerIF#setupComponent(ExtendedComponentInstanceContainerIF)
     * {@inheritDoc}
     */
    public void setupComponent(ExtendedComponentInstanceContainerIF<?, ?> instanceContainer)
            throws LifecycleException {

        performLifecyclePhase(instanceContainer, LifecycleMethod.LIFECYCLE_INITIALIZE);
        performLifecyclePhase(instanceContainer, LifecycleMethod.LIFECYCLE_START);
        performLifecyclePhaseAsync(instanceContainer, LifecycleMethod.LIFECYCLE_EXECUTE,
                LifecycleMethod.LIFECYCLE_EXECUTED);
    }

    /**
     * This method performs a
     * {@link net.sf.mmm.framework.api.ExtendedComponentDescriptorIF#getLifecycleMethod(String) lifecycle-method}
     * asynchronous in a separate thread.
     * 
     * @see #performLifecyclePhase(ExtendedComponentInstanceContainerIF, String)
     * 
     * @param instanceContainer
     *        is the container with the component instance.
     * @param phase
     *        is the
     *        {@link net.sf.mmm.framework.api.ExtendedComponentDescriptorIF#getLifecycleMethod(String) phase}
     *        to perform.
     * @param endState
     *        is the
     *        {@link ExtendedComponentInstanceContainerIF#getLifecycleState() lifecycle-state}
     *        reached if the phase has completed.
     */
    protected void performLifecyclePhaseAsync(
            ExtendedComponentInstanceContainerIF<?, ?> instanceContainer, String phase,
            String endState) {

        LifecycleMethod executeMethod = instanceContainer.getDescriptor().getLifecycleMethod(phase);
        if (executeMethod != null) {
            LifecycleRunner runner = new LifecycleRunner(instanceContainer, phase, endState);
            this.threadPool.execute(runner);
        }
    }

    /**
     * @see net.sf.mmm.framework.api.LifecycleManagerIF#shutdownComponent(ExtendedComponentInstanceContainerIF)
     * {@inheritDoc}
     */
    public void shutdownComponent(ExtendedComponentInstanceContainerIF<?, ?> instanceContainer)
            throws LifecycleException {

        String state = instanceContainer.getLifecycleState();
        if (LifecycleMethod.LIFECYCLE_EXECUTE.equals(state)
                || LifecycleMethod.LIFECYCLE_START.equals(state)
                || LifecycleMethod.LIFECYCLE_PAUSE.equals(state)
                || LifecycleMethod.LIFECYCLE_RESUME.equals(state)) {
            performLifecyclePhase(instanceContainer, LifecycleMethod.LIFECYCLE_STOP);
            // state = LifecycleMethod.LIFECYCLE_STOP;
        }
        if (!LifecycleMethod.LIFECYCLE_DISPOSE.equals(state)) {
            performLifecyclePhase(instanceContainer, LifecycleMethod.LIFECYCLE_DISPOSE);
        }
    }
    
    /**
     * This inner class is used to execute lifecycle methods asynchronously.
     */
    protected static class LifecycleRunner implements Runnable {

        /** the instance container */
        private final ExtendedComponentInstanceContainerIF<?, ?> instanceContainer;

        /** the lifecycle phase to run */
        private final String phase;

        /** the lifecycle state if completed */
        private final String endState;

        /** the logger */
        private Log logger;

        /**
         * The constructor.
         * 
         * @param componentInstanceContainer
         * @param lifecyclePhase
         * @param lifecycleEndStart
         */
        public LifecycleRunner(
                ExtendedComponentInstanceContainerIF<?, ?> componentInstanceContainer,
                String lifecyclePhase, String lifecycleEndStart) {

            super();
            this.instanceContainer = componentInstanceContainer;
            this.phase = lifecyclePhase;
            this.endState = lifecycleEndStart;
        }

        /**
         * @see java.lang.Runnable#run()
         * {@inheritDoc}
     */
        public void run() {

            try {
                LifecycleMethod method = this.instanceContainer.getDescriptor().getLifecycleMethod(
                        this.phase);
                if (method == null) {
                    this.logger.warn("Lifecycle phase " + this.phase + " not supported by "
                            + this.instanceContainer);
                } else {
                    this.instanceContainer.setLifecycleState(this.phase);
                    method.getLifecycleMethod().invoke(this.instanceContainer.getPrivateInstance(),
                            ReflectionUtil.NO_ARGUMENTS);
                }
            } catch (Throwable t) {
                this.logger.error("Asynchronous lifecycle phase " + this.phase + " of "
                        + this.instanceContainer + " threw an exception!", t);
            } finally {
                this.instanceContainer.setLifecycleState(this.endState);
            }
        }
    }

}
