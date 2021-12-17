# Flow Control Plugin

A Rundeck plugin to control execution flow with conditions.

### Usage

Select the Conditional Flow control plugin when creating a job.

![Select](doc-select.png)

Configure your condition

With == and != you can use String or Numeric values.
![Condition](doc-condition-1.png)

With >=,>,<,<= you need use Numeric values.
![Condition](doc-condition-2.png)

If "Halt" is checked, halt when condition is satisfied. If unchecked, halt when condition is unsatisfied.
If "Fail" is checked, then if it halt, it is with failed status.
If "Status" is not empty, then if it halt, it is with this custom status.
![Configure](doc-configure.png)

If "Halt Message" is not empty, then if it halt, print this message.
![Message](doc-haltmessage.png)