/*
 * Copyright 2016 SimplifyOps, Inc. (http://simplifyops.com)
 *
 * Modifications copyright (C) 2021 T. JURGA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package org.rundeck.plugin.conditionalflowcontrol;

import com.dtolabs.rundeck.core.execution.workflow.steps.StepException;
import com.dtolabs.rundeck.core.plugins.Plugin;
import com.dtolabs.rundeck.plugins.ServiceNameConstants;
import com.dtolabs.rundeck.plugins.descriptions.PluginDescription;
import com.dtolabs.rundeck.plugins.descriptions.PluginProperty;
import com.dtolabs.rundeck.plugins.step.PluginStepContext;
import com.dtolabs.rundeck.plugins.step.StepPlugin;
import com.dtolabs.rundeck.plugins.descriptions.SelectValues;

import java.util.Map;

/**
 * Step to allow halting workflow progress with a custom status
 */
@Plugin(name = ConditionalFlowControlWorkflowStep.PROVIDER_NAME, service = ServiceNameConstants.WorkflowStep)
@PluginDescription(title = "Conditional Flow Control", description = "Conditional Control Workflow execution behavior.\n\n *Halt* indicates that the execution should halt if the condition is true. Else halt if condition is false.\n Enter a *Status* to halt with a custom status string. Otherwise, enable *Fail* to exit with failure, or not to exit with success.")

public class ConditionalFlowControlWorkflowStep implements StepPlugin {

    public static final String PROVIDER_NAME = "conditional-flow-control";
    @PluginProperty(title = "First Value", description = "Compare this", required = true)
    String value1;
    @PluginProperty(title = "Condition",
                    description = "Condition for action.",
                    required = true,
                    defaultValue = "==")
    @SelectValues(freeSelect = false, values = {">=",">","==","!=","<","<="})
    String condition;
    @PluginProperty(title = "Second Value", description = "To this", required = true)
    String value2;
    @PluginProperty(title = "Halt", description = "If checked, halt when condition is satisfied. If unchecked, halt when condition is unsatisfied.", defaultValue = "false")
    boolean halt;
    @PluginProperty(title = "Fail", description = "Halt with fail result?", defaultValue = "false")
    boolean fail;
    @PluginProperty(title = "Status", description = "Use a custom exit status message.")
    String status;
    @PluginProperty(title = "Halt Message", description = "Print this message if halt.")
    String haltmessage;

    @Override
    public void executeStep(final PluginStepContext context, final Map<String, Object> configuration)
            throws StepException
    {
        boolean validation=false;
        if (condition.equals(">=") || condition.equals(">") || condition.equals("<") || condition.equals("<=")) {
            float v1=Float.parseFloat(value1.replace(",","."));
            float v2=Float.parseFloat(value2.replace(",","."));
            switch(condition){
                case ">=":
                    if (v1 >= v2) {validation=true;}
                    break;
                case "<=":
                    if (v1 <= v2) {validation=true;}
                    break;
                case "<":
                    if (v1 < v2) {validation=true;}
                    break;
                case ">":
                    if (v1 > v2) {validation=true;}
                    break;
            }
        } else {
            switch(condition){
                case "==":
                    if (value1.equals(value2)) {validation=true;}
                    break;
                case "!=":
                    if (!value1.equals(value2)) {validation=true;}
                    break;
            }
        }
        if ((validation && halt) || (!validation && !halt)) {
            if (null == context.getFlowControl()) {
                context.getLogger().log(
                            0,
                            "[" +
                            PROVIDER_NAME +
                            "] HALT requested, but no FlowControl available in this context"
                    );
                return;
            }
            if (null != haltmessage) {
                context.getLogger().log(2,haltmessage);
            }
            if (null != status) {
                context.getFlowControl().Halt(status);
            } else {
                context.getFlowControl().Halt(!fail);
            }
        } else if (context.getFlowControl() != null) {
            context.getFlowControl().Continue();
        }
    }
}
