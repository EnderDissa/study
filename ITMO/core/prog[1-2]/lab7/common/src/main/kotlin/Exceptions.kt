package common

open class CommonExceptions(string: String) : Exception(string) {}

class ScriptNameException(string: String) : CommonExceptions(string) {}
class SetOverflowException(string: String): CommonExceptions(string) {}
class CommandArgumentException(string: String): CommonExceptions(string) {}
class ValueLessThanZeroException(string: String): CommonExceptions(string) {}
class MaxValueException(string: String): CommonExceptions(string) {}
class EmptyStringException(string: String): CommonExceptions(string) {}
class RecursionScriptException(string: String): CommonExceptions(string) { }
class NullEnvironmentException(string: String): CommonExceptions(string) {}