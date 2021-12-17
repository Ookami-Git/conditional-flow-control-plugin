# Flow Control Plugin

A Rundeck plugin that adds conditional control flow.

### Usage

Select the Conditional Flow control plugin when creating a job.

![Select](doc-select.png)

Configure your condition

With == and != you can use String or Numeric values.

![Condition1](doc-condition-1.png)

With >=,>,<,<= you need use Numeric values.

![Condition2](doc-condition-2.png)

If "Halt" is checked, halt when condition is satisfied. If unchecked, halt when condition is unsatisfied.
If "Fail" is checked, then if it halts, it does so with failed status.
If "Status" is not empty, then if it halts, it does so with this custom status.

![Configure](doc-configure.png)

If "Halt Message" is not empty, then if it halts, it prints this message.

![Message](doc-haltmessage.png)
