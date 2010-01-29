class Numeric
  ##% + : a -> self
  def +(other) end
  ##% - : a -> self
  def -(other) end
  ##% == : a -> Boolean
  def ==(other) end
  ##% abs : () -> self
  def abs() end
  ##% ceil : () -> Fixnum
  def ceil() end
  ##% floor : () -> Fixnum
  def floor() end
  ##% round : () -> Fixnum
  def round() end
  ##% truncate : () -> Fixnum
  def truncate() end
  ##% coerce : a -> (a, self)
  def coerce(other) [number, self] end
  ##% div : a -> Fixnum
  def div(other) end
  ##% quo : a -> Float or Rational
  def quo(other) end
  ##% divmod : a -> (Fixnum, Fixnum or Float)
  def divmod(other) end
  ##% integer? : () -> Boolean
  def integer?() end
  ##% modulo : a -> Fixnum or Float
  def module(other) end
  ##% nonzero? : () -> self
  def nonzero?() end
  ##% to_int : () -> Fixnum
  def to_int() end
  ##% zero? : () -> Boolean
  def zero?() end
  ##% step : (a, ?b) {a or b -> ?} -> self
  def step(limit, step = 1) end
end

class Integer < Numeric
  ##% self.induced_from : a -> Integer
  def self.induced_from(num) end
  
  ##% [] : a -> Fixnum
  def [](nth) end
  ##% + : a -> self
  def +(other) end
  ##% - : a -> self
  def -(other) end
  ##% "*" : a -> self
  def *(other) end
  ##% / : a -> self
  def /(other) end
  ##% % : a -> self
  def %(other) end
  ##% .. : a -> self
  def **(other) self end
  ##% "<=>" : a -> self
  def <=>(other) end
  ##% == : a -> Boolean
  def ==(other) end
  ##% "<" : a -> Boolean
  def <(other) end
  ##% "<=" : a -> Boolean
  def <=(other) end
  ##% ">" : a -> Boolean
  def >(other) end
  ##% ">=" : a -> Boolean
  def >=(other) end
  ##% ~ : () -> self
  def ~() end
  ##% | : a -> self
  def |(other) end
  ##% & : a -> self
  def &(other) end
  ##% ^ : a -> self
  def ^(other) end
  ##% "<<" : a -> self
  def <<(bits) end
  ##% ">>" : a -> self
  def >>(bits) end
  ##% chr : () -> String
  def chr() '' end
  ##% downto : a {self or a -> ?} -> self
  def downto(min) end
  ##% next : () -> self
  def next() end
  ##% succ : () -> self
  def succ() end
  ##% times : () {self -> ?} -> self
  def times() end
  ##% to_int : () -> self
  def to_int() end
  ##% to_i : () -> self
  def to_i() end
  ##% size : () -> Fixnum
  def size() end
  ##% to_f : () -> Float
  def to_f() end
  ##% to_s : ?Fixnum -> String
  def to_s(base = 10) end
  ##% upto : a {self or a -> ?} -> self
  def upto(max) end
end

module Precision
  ##% prec : a -> self
  def prec(klass) end
  ##% prec_i : () -> Integer
  def prec_i() end
  ##% prec_f : () -> Float
  def prec_f() end
end

class Float < Numeric
  DIG = 0
  EPSILON = 0.0
  MANT_DIG = 0
  MAX = 0.0
  MIN = 0.0
  MAX_10_EXP = 0
  MIN_10_EXP = 0
  RADIX = 0
  ROUNDS = 0
 
  include Precision
 
  ##% self.induced_from : a -> Float
  def self.induced_from(num) end
 
  ##% + : a -> self
  def +(other) end
  ##% - : a -> self
  def -(other) end
  ##% "*" : a -> self
  def *(other) end
  ##% / : a -> self
  def /(other) end
  ##% % : a -> self
  def %(other) end
  ##% "**" : a -> self
  def **(other) end
  ##% "<=>" : a -> Boolean
  def <=>(other) end
  ##% == : a -> Boolean
  def ==(other) end
  ##% "<" : a -> Boolean
  def <(other) end
  ##% "<=" : a -> Boolean
  def <=(other) end
  ##% ">" : a -> Boolean
  def >(other) end
  ##% ">=" : a -> Boolean
  def >=(other) end
  ##% finite? : () -> Boolean
  def finite?() end
  ##% infinite? : () -> Boolean
  def infinite?() end
  ##% nan? : () -> Boolean
  def nan?() Boolean end
  ##% to_f : () -> Float
  def to_f() end
  ##% to_i : () -> Fixnum
  def to_i() 0 end
  ##% truncate : () -> Fixnum
  def truncate() 0 end
end

##% Enumerable<t>
module Enumerable
  ##% all? : () -> Boolean
  ##% all? : () {t -> ?} -> Boolean
  def all?() end
  ##% any? : () -> Boolean
  ##% any? : () {t -> ?} -> Boolean
  def any?() end
  ##% map<v> : () {t -> v} -> Array<v>
  def map() end
  alias :collect :map
  ##% each_with_index : () {(t, Fixnum) -> ?} -> self
  def each_with_index() end
  ##% find<v> : ?ifnone {t -> v} -> v of ifnone
  def find(ifnone = nil) end
  alias :detect :find
  ##% find_all : () {t -> ?} -> Array<t>
  def find_all() end
  alias :select :find_all
  ##% grep : a -> Array<t>
  ##% grep : a {t -> ?} -> Array<t>
  def grep(pattern) end
  ##% inject : ?init {(r or init, t) -> r} -> r or init
  def inject(init = nil) end
  ##% member? : a -> Boolean
  def member?(val) end
  ##% include? : a -> Boolean
  def include?(val) end
  ##% max : () -> t
  ##% max : () {(a, b) -> ?} -> t
  def max() end
  ##% max_by : () {t -> ?} -> t
  def max_by() end
  ##% min : () -> t
  ##% min : () {(t, t) -> ?} -> t
  def min() end
  ##% min_by : () {t -> ?} -> t
  def min_by() end
  ##% partition : () {t -> ?} -> (t, t)
  def partition() end
  ##% reject : () {t -> ?} -> Array<t>
  def reject() end
  ##% sort : () -> Array<t>
  ##% sort : () {(t, t) -> ?} -> Array<t>
  def sort() end
  ##% sort_by : () {t -> ?} -> Array<t>
  def sort_by() end
  ##% to_a : () -> Array<t>
  def to_a() end
  alias :entries :to_a
  # FIXME
  def zip() end
end
 
module Comparable
  ##% === : (a) -> Boolean end
  def ===(other) end
  ##% ">" : (a) -> Boolean end
  def >(other) Boolean end
  ##% ">=" : (a) -> Boolean end
  def >=(other) Boolean end
  ##% "<" : (a) -> Boolean end
  def <(other) Boolean end
  ##% "<=" : (a) -> Boolean end
  def <=(other) Boolean end
  ##% between? : (a, b) -> Boolean end
  def between?(min, max) Boolean end
end
 
##% Range<t>
class Range
  include Enumerable

  ##% self.new : (a, b, ?Boolean) -> Range<a or b>
  def self.new() end

  ##% === : a -> Boolean
  def ===(other) end
  ##% include? : a -> Boolean
  def include?(other) end
  ##% begin : () -> t
  def begin() end
  alias :start :begin
  ##% each : () {t -> ?} -> self
  def each() end
  ##% end : () -> t
  def end() end
  alias :last :end
  ##% exclude_end? : () -> Boolean
  def exclude_end?() end
  ##% length : () -> Fixnum
  def length() end
  alias :size :length
  ##% step : ?Fixnum {t -> ?} -> self
  def step(s=1) end
end

##% Array<t>
class Array
  # FIXME to_ary for +, -, &

  include Enumerable
 
  ##% self.[] : (*a) -> a
  def self.[](*item) end
  ##% self.new : (?Fixnum, ?a) -> Array<a>
  ##% self.new<a> ; a <= Array : ?a  -> a
  ##% self.new<v> : Fixnum {Fixnum -> v} -> Array<v>
  def self.new(*) end
  ##% [] : Fixnum -> t
  ##% [] : (Fixnum, Fixnum) -> Array<t>
  def [](*) end
  ##% []=<v> ; v <= t : (Fixnum, v) -> v
  ##% []=<v> ; v <= t : (Fixnum, Fixnum, Array<v>) -> Array<v>
  ##% []=<v> ; v <= t : (Range, Array<v>) -> Array<v>
  def []=(start, length, val) end
  ##% + : Array<v> -> Array<t or v>
  def +(other) end
  ##% "*" : Fixnum -> Array<t>
  ##% "*" : String -> String
  def *(times) end
  ##% -<v> : Array<v> -> Array<t or v>
  def -(other) end
  ##% - : a -> Array<t>
  def &(other) end
  ##% |<v> : Array<v> -> Array<t or v>
  def |(other) end
  ##% "<<"<v> ; v <= t : v -> self
  def <<(obj) end
  ##% "<=>" : a -> Fixnum
  def <=>(other) end
  ##% == : a -> Boolean
  def ==(other) end
  # FIXME
  ##% assoc<k, v> ; t <= (k, v) : k -> v
  def assoc(key) end
  ##% at : Fixnum -> t
  def at(pos) end
  # FIXME
  ##% clear : () -> self
  def clear() end
  # FIXME
  ##% clone : () -> Array<t>
  def clone() end
  # FIXME
  ##% map!<v> ; v <= t : () {t -> v} -> self
  def map!() end
  alias :collect! :map!
  ##% compect : () -> Array<t>
  def compact() self end
  # FIXME
  ##% compact! : () -> self
  def compact!() self end
  ##% concat<v> ; v <= t : Array<v> -> self
  def concat(other) end
  ##% delete : a -> a
  ##% delete<v> : a {() -> v} -> a or v
  def delete(val) end
  ##% delete_at : Fixnum -> t
  def delete_at(pos) end
  ##% delete_if : () {t -> ?} -> self
  def delete_if() end
  ##% reject! : () {t -> ?} -> self
  def reject() end
  ##% each : () {t -> ?} -> self
  def each() end
  ##% each_index : () {Fixnum -> ?} -> self
  def each_index() end
  ##% empty? : () -> Boolean
  def empty?() Boolean end
  ##% fetch : Fixnum -> t
  ##% fetch : (Fixnum, ifnone) -> t or ifnone
  ##% fetch<v> : Fixnum {Fixnum -> v} -> t or v
  def fetch(nth, ifnone = nil) end
  # FIXME
  ##% fill<v> ; v <= t : v -> self
  ##% fill<v> ; v <= t : (v, Fixnum, ?Fixnum) -> self
  ##% fill<v> ; v <= t : (v, Range) -> self
  ##% fill<v> ; v <= t : () {Fixnum -> v} -> self
  ##% fill<v> ; v <= t : (Fixnum, ?Fixnum) {Fixnum -> v} -> self
  ##% fill<v> ; v <= t : Range {Fixnum -> v} -> self
  def fill(*) end
  ##% first : () -> t
  ##% first : Fixnum -> Array<t>
  def first(n = nil) end
  # FIXME
  ##% flatten : () -> Array<t>
  def flatten() end
  # FIXME
  ##% flatten! : () -> Array<t>
  def flatten!() end
  ##% include?<v> : v -> Boolean
  def include?(val) end
  ##% index<v> : v -> Fixnum
  ##% index<v> : () {Fixnum -> ?} -> Fixnum
  def index(val) end
  ##% indexes : *a -> Array<t>
  def indexes(*index) end
  alias :indices :indexes
  ##% insert<v> ; v <= Array<t> : (Fixnum, *v) -> self
  def insert(nth, *val) end
  ##% join : ?String -> String
  def join(sep = $,) end
  ##% last : () -> t
  ##% last : Fixnum -> Array<t>
  def last(n = nil) end
  ##% length : () -> Fixnum
  def length() end
  alias :size :length
  def size() 0 end
  ##% nitems : () -> Fixnum
  def nitems() 0 end
  ##% pack : String -> String
  def pack(template) end
  ##% pop : () -> t
  def pop() end
  ##% push<v> ; v <= Array<t> : (*v) -> self
  def push(*obj) end
  # FIXME
  ##% rassoc<k, v> ; t <= (k, v) : v -> k
  def rassoc(obj) end
  # FIXME
  ##% replace : Array<t> -> self
  def replace(another) end
  ##% reverse : () -> Array<t>
  def reverse() end
  ##% reverse! : () -> self
  def reverse!() end
  ##% reverse_each : () {t -> ?} -> self
  def reverse_each() end
  ##% rindex<v> : v -> Fixnum
  ##% rindex<v> : () {Fixnum -> ?} -> Fixnum
  def rindex(val) end
  ##% shift : () -> t
  def shift() end
  alias :slice :[]
  alias :slice! :[]
  ##% slice! : (Fixnum, ?Fixnum) -> Array<t>
  ##% slice! : Range -> Array<t>
  def slice!(start, length) end
  # FIXME
  ##% sort : () -> Array<t>
  ##% sort : () {(t, t) -> ?} -> Array<t>
  def sort() end
  ##% sort! : () -> self
  ##% sort! : () {(t, t) -> ?} -> self
  def sort!() end
  ##% to_a : () -> self
  def to_a() end
  alias :to_ary :to_a
  # FIXME
  ##% transpose : () -> self
  def transpose() end
  ##% uniq : () -> Array<t>
  def uniq() end
  ##% uniq! : () -> self
  def uniq!() end
  ##% unshift<v> ; v <= Array<t> : (*v) -> self
  def unshift(*obj) end
  # FIXME
  ##% values_at<k, v> ; t <= (k, v) : *a -> Array<v>
  def values_at(*index) end
end

##% Hash<k, v, z>
class Hash
  include Enumerable
 
  # FIXME
  ##% self.[] : (?k1, ?v1, ?k2, ?v2, ?k3, ?v3, ?k4, ?v4, ?k5, ?v5) -> Hash<k1 or k2 or k3 or k4 or k5, v1 or v2 or v3 or v4 or v5>
  ##% self.[] : Hash<k', v', z'> -> Hash<k', v', z'>
  def self.[](*) end
  # FIXME
  ##% self.new : ?z' -> Hash<?, ?, z'>
  ##% self.new : () {(k', v') -> ?} -> Hash<?, ?, ?>
  def self.new(*) end

  ##% [] : a -> v
  def [](key) end
  ##% []= : (k, v) -> v
  def []=(key, value) end
  ##% store : (k, v) -> v
  def store(key, value) end
  # FIXME
  def clear() end
  # FIXME
  ##% clone : () -> Hash<k, v, z>
  def clone() end
  alias :dup :clone
  ##% default : ?a -> d or a
  def default(key=nil) end
  ##% default<d> ; d <= z: d -> d
  def default=(value) end
  # FIXME
  def default_proc() end
  ##% delete : a -> v
  ##% delete : a {a -> b} -> b
  def delete(key) end
  ##% reject : () {(k, v) -> ?} -> Hash<k, v>
  def reject() end
  ##% delete_if : () {(k, v) -> ?} -> self
  def delete_if() end
  alias :reject! :delete_if
  ##% each : () {(k, v) -> ?} -> self
  def each() end
  ##% each_pair : () {(k, v) -> ?} -> self
  def each_pair() end
  ##% each_key : () {k -> ?} -> self
  def each_key() end
  ##% each_value : () {v -> ?} -> self
  def each_value() end
  ##% empty? : () -> Boolean
  def empty?() end
  ##% fetch : (a, ?d) -> v or d
  ##% fetch : a {a -> d} -> v or d
  def fetch(key) end
  ##% has_key? : a -> Boolean
  def has_key?(key) end
  alias :include? :has_key?
  alias :key? :has_key?
  alias :member? :has_key?
  ##% has_value? : a -> Boolean
  alias :value? :has_value?
  ##% indexes : *a -> Hash<k, v>
  def indexes(*index) end
  alias :indices :indexes
  ##% invert : () -> Hash<v, k>
  def invert() end
  ##% keys : () -> Array<k>
  def keys() end
  ##% length : () -> Fixnum
  def length() end
  alias :size :length
  ##% merge<a> ; a <= Hash<k', v', z'> : a -> Hash<k or k', v or v', z or z'>
  ##% merge<a> ; a <= Hash<k', v', z'> : a {(k, v, v') -> v''} -> Hash<k or k', v'', z or z'>
  def merge(other) end
  ##% merge!<a> ; a <= Hash<k, v, z> : a -> self
  ##% merge!<a> ; a <= Hash<k, v', z>, v'' <= v : a {(k, v, v') -> v''} -> self
  def merge!(other) end
  ##% rehash : () -> self
  def rehash() end
  ##% replace<a> ; a <= Hash<k, v, z> : a -> self
  def replace(other) end
  ##% shift : () -> (k, v) or z
  def shift() end
  ##% to_a : () -> Array<(k, v)>
  def to_a() end
  ##% to_hash : () -> self
  def to_hash() end
  ##% update<a> ; a <= Hash<k, v, z> : a -> self
  ##% update<a> ; a <= Hash<k, v', z>, v'' <= v : a {(k, v, v') -> v''} -> self
  def update(other) end
  ##% values : () -> Array<v>
  def values() end
  ##% values_at : *a -> Array<v or z>
  def values_at(*key) end
end

class Object
  alias :type :class
  
  ##% dup : () -> self
  def dup() end
  ##% clone : () -> self
  def clone() end
  ##% == : a -> Boolean
  def ==(other) end
  ##% === : a -> Boolean
  def ===(other) end
  ##% =~ : a -> Boolean
  def =~(other) end
  ##% display : ?IO -> nil
  def display(out = $stdout) end
  ##% eql? : a -> Boolean
  def eql?(other) end
  ##% equal? : a -> Boolean
  def equal?(other) end
  # FIXME
  ##% extend : *a -> self
  def extend(*mod) end
  ##% freeze : () -> self
  def freeze() end
  ##% frozen? : () -> Boolean
  def frozen?() end
  ##% hash : () -> Fixnum
  def hash() end
  ##% id : () -> Fixnum
  def id() end
  alias :object_id :id
  ##% p : () -> String
  def p() end
  ##% instance_eval : (String, ?String, ?Fixnum) -> self
  ##% instance_eval : () {self -> a} -> a
  def instance_eval(*) end
  ##% instance_of : ?Class -> Boolean
  def instance_of(klass) end
  # FIXME
  ##% instance_variable_get : a -> ?
  def instance_variable_get(var) end
  # FIXME
  ##% instance_variable_set : (a, b) -> b
  def instance_variable_set(var, val) end
  ##% instance_variables : () -> Array<String>
  def instance_variables() end
  ##% is_a?<a> ; a <= Module : a -> Boolean
  def is_a?(mod) end
  ##% kind_of?<a> ; a <= Module : a -> Boolean
  def kind_of?(mod) end
  ##% method : String or Symbol -> Method
  def method(name) end
  # FIXME
  def method_missing(name, *arg) end
  ##% methods : ?Boolean -> Array<String>
  def methods() end
  alias :public_methods :methods
  alias :private_methods :methods
  alias :protected_methods :methods
  alias :singleton_methods :methods
  ##% nil? : () -> Boolean end
  def nil?() end
  ##% respond_to? : (String or Symbol, ?Boolean) -> Boolean
  def respond_to?() end
  ##% send : (String or Symbol, *a) -> ?
  def send(name, *arg) end
  alias :__send__ :send
  ##% taint : () -> self
  def taint() end
  ##% tainted? : () -> Boolean
  def tainted?() end
  ##% to_a : () -> Array
  def to_a() end
  ##% to_ary : () -> Array
  def to_ary() end
  ##% to_hash : () -> Hash
  def to_hash() end
  ##% to_int : () -> Integer
  def to_int() end
  ##% to_s : () -> String
  def to_s() end
  ##% to_str : () -> String
  def to_str() end
  ##% untaint : () -> self
  def untaint() end
end

##% String ; t <= String
class String
  include Comparable
  include Enumerable

  ##% self.new : ?String -> String
  def self.new(string = nil) end

  ##% + : String -> String
  def +(other) end
  ##% "*" : Fixnum -> String
  def *(times) end
  ##% % : a -> String
  def %(args) end
  ##% == : String -> Boolean
  def ==(other) end
  ##% ">" : String -> Boolean
  def >(other) end
  ##% ">=" : String -> Boolean
  def >=(other) end
  ##% "<" : String -> Boolean
  def <(other) end
  ##% "<=" : String -> Boolean
  def <=(other) end
  ##% "<<" : String or Fixnum -> self
  def <<(other) end
  ##% concat : String or Fixnum -> self
  def concat(other) end
  ##% =~ : Regexp -> Fixnum
  def =~(regexp) end
  ##% [] : Fixnum -> Fixnum
  ##% [] : (Fixnum, Fixnum) -> String
  ##% [] : String -> String
  ##% [] : Regexp -> String
  ##% [] : (Regexp, Fixnum) -> String
  ##% [] : Range -> String
  def [](*) end
  ##% []= : (Fixnum, String) -> Fixnum
  ##% []= : (Fixnum, Fixnum, String) -> String
  ##% []= : (String, String) -> String
  ##% []= : (Regexp, String) -> String
  ##% []= : (Regexp, Fixnum, String) -> String
  ##% []= : (Range, String) -> String
  def []=(*) end
  ##% "<=>" : String -> Fixnum
  def <=>(other) end
  ##% capitalize : () -> String
  def capitalize() self end
  ##% capitalize! : () -> self
  def capitalize!() self end
  ##% casecmp : String -> Fixnum
  def casecmp(other) end
  ##% center : (String, ?String) -> String
  def center(width, padding = nil) self end
  ##% ljust : (String, ?String) -> String
  def ljust(width, padding = nil) self end
  ##% rjust : (String, ?String) -> String
  def rjust(width, padding = nil) self end
  ##% chomp : ?String -> String
  def chomp(rs = $/) end
  ##% chomp! : ?String -> self
  def chomp!(rs = $/) end
  ##% chop : () -> String
  def chop() end
  ##% chop! : () -> String
  def chop!() end
  # FIXME
  def clear() end
  ##% clone : () -> String
  def clone() end
  ##% dup : () -> String
  def dup() end
  ##% count : (String, *String) -> Fixnum
  def count(*str) end
  ##% crypt : String -> String
  def crypt() end
  ##% delete : (String, *String) -> String
  def delete(*str) end
  ##% delete! : (String, *String) -> self
  def delete!(*str) end
  ##% downcase : () -> String
  def downcase() end
  ##% downcase! : () -> self
  def downcase!() end
  ##% dump : () -> String
  def dump() end
  ##% each : ?String {String -> ?} -> self
  def each(rs = $/) end
  alias :each_line :each
  ##% each_byte : () {Fixnum -> ?} -> self
  def each_byte() end
  ##% empty? : () -> Boolean
  def empty?() end
  ##% gsub : (String or Regexp, String) -> String
  ##% gsub : String or Regexp {String -> ?} -> String
  def gsub(pattern, replace = nil) end
  alias :gsub! :gsub
  ##% hex : () -> Fixnum
  def hex() end
  ##% include? : String or Fixnum -> Boolean
  def include?(substr) end
  ##% index : (String or Regexp or Fixnum, ?Fixnum) -> Fixnum
  def index(pattern, pos = 0) end
  ##% insert : (Fixnum, String) -> self
  def insert(nth, other) end
  ##% intern : () -> Symbol
  def intern() end
  alias :to_sym :intern
  ##% length : () -> Fixnum
  def length() end
  alias :size :length
  ##% match : (String or Regexp, ?Fixnum) -> MatchData
  def match(regexp, pos = 0) end
  ##% next : () -> String
  def next() end
  alias :next! :next
  alias :succ :next
  alias :succ! :next
  ##% oct : () -> Fixnum
  def oct() end
  ##% replace : String -> self
  def replace(other) end
  ##% reverse : () -> String
  def reverse() end
  ##% reverse : () -> self
  def reverse!() end
  ##% rindex : (String or Regexp or Fixnum, ?Fixnum) -> Fixnum
  def rindex(pattern, pos = 0) end
  ##% scan : String or Regexp -> Array<String>
  ##% scan : String or Regexp {String -> ?} -> self
  def scan(re) end
  alias :slice :[]
  alias :slice! :[]
  ##% split : (?(String or Regexp), ?Fixnum) -> Array<String>
  def split(sep = $:, limit = nil) end
  ##% squeeze : *String -> String
  def squeeze(*str) end
  ##% squeeze : *String -> self
  def squeeze!(*str) end
  ##% strip : () -> String
  def strip() self end
  alias :strip! :strip
  alias :lstrip :strip
  alias :lstrip! :strip
  alias :rstrip :strip
  alias :rstrip! :strip
  ##% sub : (String or Regexp or Fixnum, String) -> String
  ##% sub : String or Regexp or Fixnum {String -> ?} -> self
  def sub(pattern, replace = nil) end
  ##% sum : ?Fixnum -> Fixnum
  def sum(bits=16) end
  ##% swapcase : () -> String
  def swapcase() end
  ##% swapcase! : () -> self
  def swapcase!() end
  ##% to_f : () -> Float
  def to_f() end
  ##% to_i : ?Fixnum -> Fixnum
  def to_i(base = 10) end
  ##% to_s : () -> self
  def to_s() end
  alias :to_str :to_s
  ##% tr : (String, String) -> String
  def tr(search, replace) end
  alias :tr! :tr
  alias :tr_s :tr
  alias :tr_s! :tr
  ##% unpack : String -> Array
  def unpack(template) end
  ##% upcase : () -> String
  def upcase() end
  ##% upcase : () -> self
  def upcase!() end
  ##% upto : String {String -> ?} -> self
  def upto(max) end
end

class IO; end

class File < IO
  module Constants
    LOCK_SH = 0
    LOCK_EX = 0
    LOCK_UN = 0
    LOCK_NB = 0
    RDONLY = 0
    WRONLY = 0
    RDWR = 0
    APPEND = 0
    CREAT = 0
    EXCL = 0
    NONBLOCK = 0
    TRUNC = 0
    NOCTTY = 0
    BINARY = 0
    SYNC = 0
    FNM_NOESCAPE = 0
    FNM_PATHNAME = 0
    FNM_PERIOD = 0
    FNM_CASEFOLD = 0
    FNM_DOTMATCH = 0
  end
end

##% IO ; t <= String
class IO
  include Enumerable
  include File::Constants
  
  SEEK_CUR = 0
  SEEK_END = 0
  SEEK_SET = 0
  
  def self.new(fd, mode = 0)
    io = super()
    yield io
    io
  end
 
  ##% self.open : String -> File
  ##% self.open : String {File -> a} -> ?
  def self.open(fd, mode = 0) end
 
  def self.foreach(path, rs = $/)
    yield ''
    self
  end
 
  def self.pipe() [self.new] end
 
  def self.popen(command, mode = 0)
    ui = self.new
    yield io
    io
  end
 
  def self.read(path, length = nil, offset = nil) '' end
  def self.readlines(path, rs = $/) [''] end
 
  def select(reads, writes = nil, excepts = nil, timeout = nil)
    [writes, excepts, timeout]
  end
 
  def sysopen(path, mode = 0, perm = 0) 0 end
  def <<(object) self end
  def binmode() self end
  def close_read() nil end
  def close_write() nil end
  def closed?() Boolean end
 
  def each(rs = $/)
    yield ''
    self
  end
 
  alias :each_line :each
  def each_byte() yield 0 end
  def eof() Boolean end
  alias :eof? :eof
  def fcntl(cmd, arg = 0) 0 end
  def fsync() 0 end
  def fileno() 0 end
  alias :to_i :fileno
  def flush() self end
  def getc() 0 end
  def gets(rs = $/) '' end
  def ioctl(cmd, arg = 0) 0 end
  def isatty() Boolean end
  alias :tty? :isatty
  def lineno() 0 end
  def lineno=(number) number end
  def pi() 0 end
  def pos() 0 end
  alias :tell :pos
  def print(*arg) nil end
  def printf(format, *arg) nil end
  def putc(ch) ch end
  def puts(*obj) nil end
  def read(length = nil, outbuf = nil) '' end
  alias :readchar :getc
  alias readline :gets
  def readlines(rs = $/) [''] end
  def read_nonblock(maxlen, outbuf = nil) '' end
  def readpartial(maxlen, outbuf = nil) '' end
  def reopen(io, mode = nil) self end
  def rewind() self end
  def seek(offset, whence = SEEK_SET) 0 end
  def stat() File::Stat.new end
  def sync() Boolean end
  def sync=(newstate) newstate end
  def sysread(maxlen, outbuf = nil) '' end
  def sysseek(offset, whence = SEEK_SET) 0 end
  def syswrite(string) 0 end
  def to_io() self end
  def ungetc(char) nil end
  def write(str) 0 end
  def write_nonblock(string) 0 end
end

class Rational < Numeric
end
