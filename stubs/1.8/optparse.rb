class OptionParser
  # TODO {match : (String) -> ?}
  ##% self.accept(Class, ?Object) {String -> ?} -> ?
  def self.accept(klass, pat = /.*/) yield '' end
  ##% self.getopts(Array<String>, *String) -> Hash<String, Boolean or String>
  ##% self.getopts(*String) -> Hash<String, Boolean or String>
  def self.getopts(*arg) {'' => BOOLEAN || ''} end
  ##% self.new(?String, ?Integer, ?String) {OptionParser -> ?} -> OptionParser
  ##% self.new(?String, ?Integer, ?String) -> OptionParser
  def self.new(banner = nil, width = 32, indent = ' ' * 4) super() end
  ##% self.reject(Class) -> ? end
  def self.reject(klass) end

  # TODO {match : (String) -> ?}
  ##% accept(Class, ?Object) {String -> ?} -> ?
  def accept(klass, pat = /.*/) yield '' end
  ##% banner() -> String
  def banner() '' end
  ##% banner=(String) -> String
  def banner=(heading) heading end
  ##% default_argv() -> Array<String>
  def default_argv() [''] end
  ##% default_argv=(Array<String>) -> Array<String>
  def default_argv=(argv) argv end
  ##% environment(String) -> Array<String>
  def environment(env) [''] end
  ##% getopts(Array<String>, *String) -> Hash<String, Boolean or String>
  ##% getopts(*String) -> Hash<String, Boolean or String>
  def getopts(*arg) {'' => BOOLEAN || ''} end
  ##% help() -> String
  def help() '' end
  ##% to_s() -> String
  def to_s() '' end
  ##% load(?String) -> Boolean
  def load(filename = nil) BOOLEAN end
  ##% on(String, ?String) {Boolean or String -> ?} -> self
  ##% on(String, String, ?String) {Boolean or String -> ?} -> self
  ##% on(String, ?Regexp, ?String) {Boolean or String -> ?} -> self
  ##% on(String, String, ?Regexp, ?String) {Boolean or String -> ?} -> self
  # TODO pass an instance of the class
  ##% on(String, ?Class, ?String) {Boolean or String -> ?} -> self
  ##% on(String, String, ?Class, ?String) {Boolean or String -> ?} -> self
  def on(*arg) yield(BOOLEAN || ''); self end
  ##% on_head(*a) {Boolean or String -> ?} -> self
  def on_head(*arg, &block) yield(BOOLEAN || ''); self end
  ##% on_tail(*a) {Boolean or String -> ?} -> self
  def on_tail(*arg, &block) yield(BOOLEAN || ''); self end
  ##% order(Array<String>) -> Array<String>
  ##% order(Array<String>) {String -> ?} -> Array<String>
  ##% order(*String) -> Array<String>
  ##% order(*String) {String -> ?} -> Array<String>
  def order(*arg) yield ''; [''] end
  ##% order!(?Array<String>) -> Array<String>
  ##% order!(?Array<String>) {String -> ?} -> Array<String>
  def order!(argv = self.default_argv) yield ''; [''] end
  ##% parse(Array<String>) -> Array<String>
  ##% parse(*String) -> Array<String>
  def parse(*arg) [''] end
  ##% parse!(?Array<String>) -> Array<String>
  def parse!(argv = self.default_argv) [''] end
  ##% permute(Array<String>) -> Array<String>
  ##% permute(*String) -> Array<String>
  def permute(*arg) [''] end
  ##% permute!(?Array<String>) -> Array<String>
  def permute!(argv = self.default_argv) [''] end
  ##% program_name() -> String
  def program_name() '' end
  ##% program_name=(String) -> String
  def program_name=(name) name end
  ##% reject(Class) -> ? end
  def reject(klass) end
  ##% release() -> String
  def release() '' end
  ##% release=(String) -> String
  def release=(release) release end
  ##% separator(String) -> ?
  def separator(sep) end
  # TODO {<< : Object -> ?}
  ##% summarize(?Object, ?Integer, ?Integer, ?String) -> ?
  ##% summarize(?Object, ?Integer, ?Integer, ?String) {String -> ?} -> ?
  def summarize(to = [], width = self.summary_width, max = width - 1, indent = self.summary_ident) yield '' end
  ##% summary_indent() -> String
  def summary_indent() '' end
  ##% summary_indent=(String) -> String
  def summary_indent=(indent) indent end
  ##% summary_width() -> Integer
  def summary_width() '' end
  ##% summary_width=(Integer) -> Integer
  def summary_width=(width) width end
  ##% to_a() -> Array<String>
  def to_a() [''] end
  ##% ver() -> String
  def ver() '' end
  ##% version() -> String
  def version() '' end
  ##% version=(String) -> String
  def version=(version) version end

  # TODO OptionParser::Arguable

  class ParseError < Exception; end
  class AmbiguousArgument < Exception; end
  class AmbiguousOption < Exception; end
  class InvalidArgument < Exception; end
  class InvalidOption < Exception; end
  class MissingArgument < Exception; end
  class NeedlessArgument < Exception; end

  # TODO optparse/date
  # TODO optparse/shellwords
  # TODO optparse/time
  # TODO optparse/uri
end
