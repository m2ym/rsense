# -*- coding: undecided -*-
# ruby 1.8.7 builtin library stub
# http://doc.okkez.net/187/view/library/

BOOLEAN = true || false

module Enumerable; end
module Precision; end
class IO; end
class File < IO; end
module File::Constants; end

##% Array<t>
class Array
  # FIXME to_ary for +, -, &

  include Enumerable

  ##% self.[](*a) -> a
  def self.[](*item) item end
  ##% self.new(?Integer, ?a) -> Array<a>
  ##% self.new<a | a <= Array>(?a)  -> a
  ##% self.new<v>(Integer) {Integer -> v} -> Array<v>
  def self.new(*) end

  # FIXME to_ary

  ##% &<v>(Array<v>) -> Array<t or v>
  def &(other) self || other end
  ##% "*"(Integer) -> Array<t>
  ##% "*"(String) -> String
  def *(times) self end
  ##% +(Array<v>) -> Array<t or v>
  def +(other) self || other end
  ##% -(Array<v>) -> Array<t or v>
  def -(other) self || other end
  ##% "<<"<v | v <= t>(v) -> self
  def <<(obj) self end
  ##% "<=>"(a) -> Fixnum
  def <=>(other) 0 end
  ##% ==(a) -> Boolean
  def ==(other) BOOLEAN end
  ##% [](Integer) -> t
  ##% [](Range) -> Array<t>
  ##% [](Integer, Integer) -> Array<t>
  def [](*) at(0) end
  ##% []=<v | v <= t>(Integer, v) -> v
  ##% []=<v | v <= t>(Range, Array<v>) -> Array<v>
  ##% []=<v | v <= t>(Integer, Integer, Array<v>) -> Array<v>
  def []=(*) self end
  # FIXME
  ##% assoc<k, v | t <= (k, v)>(k) -> v
  def assoc(key) [0][1] end
  ##% at(Integer) -> t
  def at(pos) _e end
  ##% choice() -> t
  def choice() [0] end
  # FIXME
  ##% clear() -> self
  def clear() self end
  ##% clone() -> Array<t>
  def clone() self end
  alias :dup :clone
  # FIXME
  ##% collect!() {t -> ?} -> self
  ##% collect!() -> Enumerator<self, t>
  def collect!() yield _e; self end
  alias :map! :collect!
  ##% combination(Integer) {Array<t> -> ?} -> Array<Array<t> >
  ##% combination(Integer) -> Enumerator<Array<Array<t> >, Array<t> >
  def combination(n) yield [_e]; [[_e]] end
  ##% compact() -> Array<t>
  def compact() self end
  ##% compact!() -> self
  def compact!() self end
  ##% concat<v | v <= t>(Array<v>) -> self
  def concat(other) self end
  ##% cycle() {t -> ?} -> self
  def cycle() yield _e; self end
  ##% delete(a) -> a
  ##% delete<v>(a) {() -> v} -> v
  def delete(val) val end
  ##% delete_at(Integer) -> t
  def delete_at(pos) _e end
  ##% delete_if() -> Enumerator<self, t>
  ##% delete_if() {t -> ?} -> self
  def delete_if() self end
  alias :reject! :delete_if
  ##% each() {t -> ?} -> self
  ##% each() -> Enumerator<self, t>
  def each() yield _e; self end
  ##% each_index() {Integer -> ?} -> self
  ##% each_index() -> Enumerator<self, Integer>
  def each_index() yield 0; self end
  ##% empty?() -> Boolean
  def empty?() Boolean end
  ##% eql?(a) -> Boolean
  def eql?(other) BOOLEAN end
  ##% fetch(Integer) -> t
  ##% fetch(Integer, ifnone) -> t or ifnone
  ##% fetch<v>(Integer) {Integer -> v} -> t or v
  def fetch(nth, ifnone = nil) _e || ifnone end
  ##% fill<v | v <= t>(v) -> self
  ##% fill<v | v <= t>() {Integer -> v} -> self
  ##% fill<v | v <= t>(v, Integer, ?Integer) -> self
  ##% fill<v | v <= t>(v, Range) -> self
  ##% fill<v | v <= t>(Integer, ?Integer) {Integer -> v} -> self
  ##% fill<v | v <= t>(Range) {Integer -> v} -> self
  def fill(*) self end
  ##% first() -> t
  ##% first(Integer) -> Array<t>
  def first(n = nil) _e end
  # FIXME
  ##% flatten(?Integer) -> Array<t>
  def flatten(lv = nil) self end
  # FIXME
  ##% flatten!(?Integer) -> self
  def flatten!(lv = nil) self end
  ##% hash() -> Integer
  def hash() 0 end
  ##% include?(a) -> Boolean
  def include?(val) Boolean end
  ##% index(a) -> Integer
  ##% index() {t -> ?} -> Integer
  def index(val) 0 end
  ##% indexes(*a) -> Array<t>
  def indexes(*index) self end
  ##% insert<v | v <= Array<t> >(Integer, *v) -> self
  def insert(nth, *val) self end
  ##% join (?String) -> String
  def join(sep = $,) '' end
  ##% last() -> t
  ##% last(Integer) -> Array<t>
  def last(n = nil) _e end
  ##% length() -> Integer
  def length() 0 end
  alias :size :length
  ##% nitems() -> Integer
  ##% nitems() {t -> ?} -> Integer
  def nitems() 0 end
  ##% pack(String) -> String
  def pack(template) '' end
  ##% permutation(Integer) {Array<t> -> ?} -> Array<Array<t> >
  ##% permutation(Integer) -> Enumerator<Array<Array<t> >, Array<t> >
  def permutation(n) yield [_e]; [[_e]] end
  ##% pop() -> t
  ##% pop(Integer) -> Array<t>
  def pop(n = 1) [0] end
  # FIXME
  def product(*) [[_e]] end
  ##% push<v | v <= Array<t> >(*v) -> self
  def push(*obj) self end
  ##% rassoc<k, v | t <= (k, v)>(v) -> k
  def rassoc(obj) [0][0] end
  # FIXME
  ##% replace(Array<t>) -> self
  def replace(another) self end
  ##% reverse() -> Array<t>
  def reverse() self end
  ##% reverse!() -> self
  def reverse!() self end
  ##% reverse_each() {t -> ?} -> self
  ##% reverse_each() -> Enumerator<self, t>
  def reverse_each() yield _e; self end
  ##% rindex<v>(v) -> Integer
  ##% rindex<v>() {Integer -> ?} -> Integer
  def rindex(val) end
  ##% shift() -> t
  ##% shift(Integer) -> Array<t>
  def shift() _e end
  ##% shuffle() -> Array<t>
  def shuffle() self end
  ##% shuffle!() -> self
  def shuffle!() self end
  alias :slice :[]
  alias :slice! :[]
  ##% sort() -> Array<t>
  ##% sort() {(t, t) -> ?} -> Array<t>
  def sort() self end
  ##% sort!() -> self
  ##% sort!() {(t, t) -> ?} -> self
  def sort!() self end
  ##% to_a() -> Array<t>
  def to_a() self end
  ##% to_ary() -> self
  def to_ary() self end
  ##% to_s() -> String
  def to_s() '' end
  # FIXME
  def transpose() self end
  ##% uniq() -> Array<t>
  def uniq() self end
  ##% uniq!() -> self
  def uniq!() self end
  ##% unshift<v | v <= Array<t> >(*v) -> self
  def unshift(*obj) self end
  # FIXME
  ##% values_at<k, v | t <= (k, v)>(*a) -> Array<v>
  def values_at(*index) self end
  # FIXME zip
  ##% "|"<v>(Array<v>) -> Array<t or v>
  def |(other) self || other end
end

class Bignum
  ### Precision
  ##% self.induced_from(a) -> Bignum
  def self.induced_from(number) 0 end
  ##% prec(Class) -> Bignum
  def prec(klass) 0 end

  ### Numeric
  ##% +@() -> self
  def +@() self end
  ##% -@() -> Bignum
  def -@() 0 end
  ##% "<=>"(other) -> Fixnum
  def <=>(other) 0 end
  ##% abs() -> Bignum
  def abs() self end
  ##% ceil() -> Bignum
  def ceil() 0 end
  ##% clone() -> self
  def clone() self end
  alias :dup :clone
  ##% coerce(Float) -> (Float, Float)
  ##% coerce(Numeric) -> (Bignum, Bignum)
  def coerce(other) [0.0, 0.0] end
  ##% div(Numeric) -> Bignum
  def div(other) 0 end
  ##% divmod(Numeric) -> (Bignum, Numeric)
  def divmod(other) [0, 0] end
  ##% eql?(Numeric) -> Boolean
  def eql?(other) BOOLEAN end
  ##% quo(Numeric) -> Float
  def quo(other) 0.0 end
  ##% fdiv(Numeric) -> Float
  def fdiv(other) 0.0 end
  ##% floor() -> Bignum
  def floor() 0 end
  ##% integer?() -> Boolean
  def integer?() BOOLEAN end
  ##% modulo(Numeric) -> Bignum
  def modulo(other) 0 end
  ##% nonzero?() -> self
  def nonzero?() self end
  ##% remainder(Numeric) -> Bignum
  def remainder(other) 0 end
  ##% round() -> Integer
  def round() 0 end
  ##% step<a | a <= Numeric>(Numeric, ?a) {self or a or Fixnum -> ?} -> self
  ##% step<a | a <= Numeric>(Numeric, ?a) -> Enumerable::Enumerator<self, self or a or Fixnum>
  def step(limit, step = 1) self end
  ##% to_int() -> Bignum
  def to_int() 0 end
  ##% truncate() -> Bignum
  def truncate() 0 end
  ##% zero?() -> Boolean
  def zero?() BOOLEAN end
end

class Binding
  def eval(*) end
end

class Class
  # FIXME self.new

  ##% _load(String) -> Class
  def _load(str) Class.new end
  def allocate() new() end
  ##% superclass() -> Class
  def superclass() Class.new end

  private
  def inherited(subclass) end
end

module Comparable
  ##% "<"(a) -> Boolean
  def <(other) BOOLEAN end
  ##% "<="(a) -> Boolean
  def <=(other) BOOLEAN end
  ##% ==(a) -> Boolean
  def ==(other) BOOLEAN end
  ##% ">"(a) -> Boolean
  def >(other) BOOLEAN end
  ##% ">="(a) -> Boolean
  def >=(other) BOOLEAN end
  ##% between?(a, b) -> Boolean
  def between?(min, max) BOOLEAN end
end

class Continuation
  # FIXME
end

class Data
end

##% Dir<| t <= String>
class Dir
  include Enumerable

  ##% self.[](*String) -> Array<String>
  def self.[](*pattern) [''] end
  ##% self.glob(String, ?Integer) -> Array<String>
  ##% self.glob(String, ?Integer) {String -> ?} -> nil
  def self.glob(pattern, flags = 0) [''] end
  ##% self.chdir() -> Fixnum
  ##% self.chdir(String) -> Fixnum
  ##% self.chdir<v>() {String -> v} -> v
  ##% self.chdir<v>(String) {String -> v} -> v
  def self.chdir(path = nil) 0 end
  ##% self.chroot(String) -> Fixnum
  def self.chroot(path) 0 end
  ##% self.delete(String) -> Fixnum
  def self.delete(path) 0 end
  ##% self.rmdir(String) -> Fixnum
  def self.rmdir(path) 0 end
  ##% self.unlink(String) -> Fixnum
  def self.unlink(path) 0 end
  ##% self.entries(String) -> Array<String>
  def self.entires(path) [''] end
  ##% self.foreach(String) {String -> ?} -> nil
  ##% self.foreach(String) -> Enumerator<nil, String>
  def foreach(path) yield ''; nil end
  ##% self.getwd() -> String
  def self.getwd() '' end
  ##% self.pwd() -> String
  def self.pwd() '' end
  ##% self.mkdir(String, ?Integer) -> Fixnum
  def self.mkdir(path, mode = 0777) 0 end
  ##% self.new(String) -> Dir
  def self.new(path) Dir.new('') end
  ##% self.open(String) -> Dir
  ##% self.open<v>(String) {Dir -> v} -> v
  def self.open(path) Dir.new('') end

  ##% close() -> nil
  def close() end
  ##% each() {String -> ?} -> self
  ##% each() -> Enumerator<self, t>
  def each() yield ''; self end
  ##% path() -> String
  def path() '' end
  ##% pos() -> Integer
  def pos() 0 end
  alias :tell :pos
  ##% pos=(Integer) -> self
  def pos=(pos) self end
  ##% seek(Integer) -> self
  def seek(pos) self end
  ##% read() -> String
  def read() '' end
  ##% rewind() -> self
  def rewind() self end
end

##% Enumerable<t>
module Enumerable
  ##% all?() -> Boolean
  ##% all?() {t -> ?} -> Boolean
  def all?() yield self; BOOLEAN end
  ##% any?() -> Boolean
  ##% any?() {t -> ?} -> Boolean
  def any?() yield self; BOOLEAN end
  ##% collect<v>() {t -> v} -> Array<v>
  def collect() [yield self] end
  alias :map :collect
  ##% count() -> Integer
  ##% count(a) -> Integer
  ##% count() {t -> ?} -> Integer
  def count(item = nil) yield self; 0 end
  ##% cycle() -> Enumerator<self, t>
  ##% cycle() {t -> ?} -> ?
  def cycle() yield self end
  # FIXME ifnone
  ##% find<v>(?a) -> Enumerator<t or a, t>
  ##% find<v>(?a) {t -> ?} -> t or a
  def find(ifnone = nil) yield self end
  alias :detect :find
  ##% drop(Integer) -> Array<t>
  def drop(n) to_a end
  ##% drop_while() -> Enumerator<Array<t>, t>
  ##% drop_while() {t -> ?} -> Array<t>
  def drop_while() yield _e end
  # FIXME 3
  ##% each_cons(Integer) -> Enumerator<nil, (t, t, t)>
  ##% each_cons(Integer) {(t, t, t) -> ?} -> nil
  def each_cons(n) yield _e, _e, _e end
  alias :enum_cons :each_cons
  ##% each_with_index() -> Enumerator<self, (t, Integer)>
  ##% each_with_index() {(t, Integer) -> ?} -> self
  def each_with_index() yield _e, 0 end
  alias :enum_with_index :each_with_index
  ##% to_a() -> Array<t>
  def to_a() [_e] end
  alias :entries :to_a
  ##% find_all() -> Enumerator<Array<t>, t>
  ##% find_all() {t -> ?} -> Array<t>
  def find_all() yield _e; [_e] end
  alias :select :find_all
  ##% find_index() -> Enumerator<Integer, t>
  ##% find_index() {t -> ?} -> Integer
  def find_index() yield _e; 0 end
  ##% first() -> t
  ##% first(Integer) -> Array<t>
  def first(n = nil) _e end
  # FIXME ===
  ##% grep(a) -> Array<t>
  ##% grep<v>(a) {t -> v} -> Array<v>
  def grep(pattern) [_e] end
  ##% group_by() -> Enumerator<Hash<?, Array<t> >, t>
  ##% group_by<v>() {t -> v} -> Hash<v, Array<t> >
  def group_by() {(yield _e) => [_e]} end
  # FIXME ==
  ##% member?(a) -> Boolean
  def member?(val) BOOLEAN end
  alias :include? :member?
  # FIXME sym
  ##% inject(?a) {(r or init or t) -> r} -> r or init
  ##% inject(Symbol) -> t
  ##% inject(a, Symbol) -> t or a
  def inject(*) _e end
  alias :reduce :inject
  ##% max() -> t
  ##% max() {(t, t) -> ?} -> t
  def max() _e end
  ##% max_by() -> Enumerator<t, t>
  ##% max_by() {t -> ?} -> t
  def max_by() yield t; t end
  ##% min() -> t
  ##% min() {(t, t) -> ?} -> t
  def min() _e end
  ##% min_by() -> Enumerator<t, t>
  ##% min_by() {t -> ?} -> t
  def min_by() yield t; t end
  ##% minmax() -> (t, t)
  ##% minmax() {(t, t) -> ?} -> (t, t)
  def minmax() [_e, _e] end
  ##% minmax_by() -> Enumerator<(t, t), t>
  ##% minmax_by() {t -> ?} -> (t, t)
  def minmax_by() yield _e; [_e, _e] end
  ##% none?() -> Boolean
  ##% none?() {t -> ?} -> Boolean
  def none?() BOOLEAN end
  ##% one?() -> Boolean
  ##% one?() {t -> ?} -> Boolean
  def one?() BOOLEAN end
  ##% partition() -> Enumerator<(t, t), t>
  ##% partition() {t -> ?} -> (t, t)
  def partition() yield _e; [_e, _e] end
  ##% reject() -> Enumerator<Array<t>, t>
  ##% reject() {t -> ?} -> Array<t>
  def reject() yield _e; [_e] end
  ##% sort() -> Array<t>
  ##% sort() {(t, t) -> ?} -> Array<t>
  def sort() [_e] end
  ##% sort_by() -> Enumerator<Array<t>, t>
  ##% sort_by() {t -> ?} -> Array<t>
  def sort_by() yield _e; [_e] end
  ##% take(Integer) -> Array<t>
  def take(n) [_e] end
  ##% take_while() -> Enumerator<Array<t>, t>
  ##% take_while() {t -> ?} -> Array<t>
  def take_while() yield _e; [_e]; end
  # FIXME
  def zip(*) [[_e]] end

  private
  # Special methods for internal use
  ##% _e() -> t
  def _e() end
end

##% Enumerator<s, t>
class Enumerator
  include Enumerable

  # FIXME self.new

  ##% each() {t -> ?} -> s
  def each() end
  ##% next() -> t
  def next() end
  ##% rewind() -> self
  def rewind() self end
  ##% to_splat() -> Array<t>
  def to_splat() [] end
  ##% with_index() -> Enumerator<s, (t, Integer)>
  ##% with_index() {(t, Integer) -> ?} -> s
  def with_index() end
end

# 1.8.7 <=> 1.8.8
Enumerable::Enumerator = Enumerator

module Errno
end

class FalseClass
  ##% &(a) -> FalseClass
  def %(other) false end
  ##% ^(a) -> Boolean
  def ^(other) BOOLEAN end
  ##% to_s() -> String
  def to_s() '' end
  ##% "|"(a) -> Boolean
  def |(other) BOOLEAN end
end

class File
  ALT_SEPARATOR = ''
  PATH_SEPARATOR = ''
  SEPARATOR = ''
  Separator = ''
  
  include File::Constants

  ##% self.atime(String or IO) -> Time
  def self.atime(filename) Time.new end
  ##% self.basename(String, ?String) -> String
  def self.basename(filename, suffix = '') '' end
  ##% self.blockdev?(String or IO) -> Boolean
  def self.blockdev?(path) BOOLEAN end
  ##% self.chardev?(String or IO) -> Boolean
  def self.chardev?(path) BOOLEAN end
  ##% self.chmod(Integer, *String) -> Integer
  def self.chmod(mode, *filename) 0 end
  ##% self.chown(Integer, Integer, *String) -> Integer
  def self.chown(owner, group, *filename) 0 end
  ##% self.ctime(String or IO) -> Time
  def self.ctime(filename) Time.new end
  ##% self.delete(*String) -> Integer
  def self.delete(*filename) 0 end
  ##% self.unlink(*String) -> Integer
  def self.unlink(*filename) 0 end
  ##% self.directory?(String or IO) -> Boolean
  def self.directory?(path) BOOLEAN end
  ##% self.dirname(String) -> String
  def self.dirname(filename) '' end
  ##% self.executable?(String or IO) ->Boolean
  def self.executable?(path) BOOLEAN end
  ##% self.executable_real?(String or IO) ->Boolean
  def self.executable_real?(path) BOOLEAN end
  ##% self.exist?(String or IO) -> Boolean
  def self.exist?(path) BOOLEAN end
  ##% self.exists?(String or IO) -> Boolean
  def self.exists?(path) BOOLEAN end
  ##% self.expand_path(String, ?String) -> String
  def self.expand_path(path, default_dir = '.') '' end
  ##% self.extname(String) -> String
  def self.extname(filename) '' end
  ##% self.file?(String or IO) -> Boolean
  def self.file?(path) BOOLEAN end
  ##% self.fnmatch(String, String, ?Integer) -> Boolean
  def self.fnmatch(pattern, path, flags = 0) BOOLEAN end
  ##% self.fnmatch?(String, String, ?Integer) -> Boolean
  def self.fnmatch?(pattern, path, flags = 0) BOOLEAN end
  ##% self.ftype(String) -> String
  def self.ftype(filename) '' end
  ##% self.grpowned?(String or IO) -> Boolean
  def self.grpowned?(filename) BOOLEAN end
  ##% self.identical?(String or IO, String or IO) -> Boolean
  def self.identical?(filename1, filename2) BOOLEAN end
  ##% self.join(*String) -> String
  def self.join(*item) '' end
  ##% self.lchmod(Integer, *String) -> Integer
  def self.lchmod(mode, *filename) 0 end
  ##% self.lchown(Integer, Integer, *String) -> Integer
  def self.lchown(owner, group, *filename) 0 end
  ##% self.link(String, String) -> Integer
  def self.link(old, new) 0 end
  ##% self.lstat(String) -> File::Stat
  def self.lstat(filename) File::Stat.new('') end
  ##% self.mtime(String or IO) -> Time
  def self.mtime(filename) Time.new end
  ##% self.new(String or Integer, ?a, ?Integer) -> File
  def self.new(path, mode = 'r', perm = 0666) File.new('') end
  ##% self.open(String or Integer, ?a, ?Integer) -> File
  ##% self.open(String or Integer, ?a, ?Integer) {File -> a} -> a
  def self.open(path, mode = 'r', perm = 0666) File.new('') end
  ##% self.owned?(String or IO) -> Boolean
  def self.owned?(path) BOOLEAN end
  ##% self.pipe?(String or IO) -> Boolean
  def self.pipe?(path) BOOLEAN end
  ##% self.readable?(String or IO) -> Boolean
  def self.readable?(path) BOOLEAN end
  ##% self.readable_real?(String or IO) -> Boolean
  def self.readable_real?(path) BOOLEAN end
  ##% self.readlink(String) -> String
  def self.readlink(path) '' end
  ##% self.rename(String, String) -> Integer
  def self.rename(from, to) 0 end
  ##% self.setgid?(String or IO) -> Boolean
  def self.setgid?(path) BOOLEAN end
  ##% self.setuid?(String or IO) -> Boolean
  def self.setuid?(path) BOOLEAN end
  ##% self.size(String or IO) -> Integer
  def self.size(path) 0 end
  ##% self.size?(String or IO) -> Boolean
  def self.size?(path) BOOLEAN end
  ##% self.socket?(String or IO) -> Boolean
  def self.socket?(path) BOOLEAN end
  ##% self.split(String) -> (String, String)
  def self.split(pathname) ['', ''] end
  ##% self.stat(String) -> File::Stat
  def self.stat(filename) File::Stat.new('') end
  ##% self.sticky?(String or IO) -> Boolean
  def self.sticky?(path) BOOLEAN end
  ##% self.symlink(String, String) -> Integer
  def self.symlink(old, new) 0 end
  ##% self.symlink?(String or IO) -> Boolean
  def self.symlinky?(path) BOOLEAN end
  ##% self.truncate(String, Integer) -> Integer
  def self.truncate(path, length) 0 end
  ##% self.umask(?Integer) -> Integer
  def self.umask(umask = 0) 0 end
  ##% self.utime(Time or Integer, Time or Integer, *String) -> Integer
  def self.utime(atime, mtime, *filename) 0 end
  ##% self.writable?(String or IO) -> Boolean
  def self.writable?(path) BOOLEAN end
  ##% self.writable_real?(String or IO) -> Boolean
  def self.writable_real?(path) BOOLEAN end
  ##% self.zero?(String or IO) -> Boolean
  def self.zero?(path) BOOLEAN end

  ##% atime() -> Time
  def atime() Time.new end
  ##% chmod(Integer) -> Integer
  def chmod(mode) 0 end
  ##% chown(Integer, Integer) -> Integer
  def chown(owner, group) 0 end
  ##% ctime() -> Time
  def ctime() Time.new end
  ##% flock(Integer) -> Integer or FalseClass
  def flock(operation) 0 || false end
  ##% lstat() -> File::Stat
  def lstat() File::Stat.new('') end
  ##% mtime() -> Time
  def mtime() Time.new end
  ##% path() -> String
  def path() '' end
  ##% truncate(Integer) -> Integer
  def truncate(length) 0 end
end

module File::Constants
  APPEND = 0
  BINARY = 0
  CREAT = 0
  EXCL = 0
  FNM_CASEFOLD = 0
  FNM_DOTMATCH = 0
  FNM_NOESCAPE = 0
  FNM_PATHNAME = 0
  FNM_SYSCASE = 0
  LOCK_EX = 0
  LOCK_NB = 0
  LOCK_SH = 0
  LOCK_UN = 0
  NOCTTY = 0
  NONBLOCK = 0
  RDONLY = 0
  RDWR = 0
  SYNC = 0
  TRUNC = 0
  WRONLY = 0
end

class File::Stat
  include Comparable

  ##% self.new(String) -> File::Stat
  def self.new(path) super() end

  ##% "<=>"(File::Stat) -> Fixnum
  def <=>(o) 0 end
  ##% atime() -> Time
  def atime() Time.new end
  ##% blksize() -> Integer
  def blksize() 0 end
  ##% blockdev?() -> Boolean
  def blockdev?() BOOLEAN end
  ##% blocks() -> Integer
  def blocks() 0 end
  ##% chardev?() -> Boolean
  def chardev?() BOOLEAN end
  ##% ctime() -> Time
  def ctime() Time.new end
  ##% dev() -> Integer
  def dev() 0 end
  ##% dev_major() -> Integer
  def dev_major() 0 end
  ##% dev_minor() -> Integer
  def dev_minor() 0 end
  ##% directory?() -> Boolean
  def directory?() BOOLEAN end
  ##% executable?() -> Boolean
  def executable?() BOOLEAN end
  ##% executable_real?() -> Boolean
  def executable_real?() BOOLEAN end
  ##% file?() -> Boolean
  def file?() BOOLEAN end
  ##% ftype() -> String
  def ftype() '' end
  ##% gid() -> Integer
  def gid() 0 end
  ##% grpowned?() -> Boolean
  def grpowned?() BOOLEAN end
  ##% ino() -> Integer
  def ino() 0 end
  ##% mode() -> Integer
  def mode() 0 end
  ##% mtime() -> Time
  def mtime() Time.new end
  ##% nlink() -> Integer
  def nlink() 0 end
  ##% owned?() -> Boolean
  def owned?() BOOLEAN end
  ##% pipe?() -> Boolean
  def pipe?() BOOLEAN end
  ##% rdev() -> Integer
  def rdev() 0 end
  ##% rdev_major() -> Integer
  def rdev_major() 0 end
  ##% rdev_minor() -> Integer
  def rdev_minor() 0 end
  ##% readable?() -> Boolean
  def readable?() BOOLEAN end
  ##% readable_real?() -> Boolean
  def readable_real?() BOOLEAN end
  ##% setgid?() -> Boolean
  def setgid?() BOOLEAN end
  ##% setuid?() -> Boolean
  def setuid?() BOOLEAN end
  ##% size() -> Integer
  def size() 0 end
  ##% size?() -> Integer or nil
  def size?() 0 || nil end
  ##% socket?() -> Boolean
  def socket?() BOOLEAN end
  ##% sticky?() -> Boolean
  def sticky?() BOOLEAN end
  ##% symlink?() -> FalseClass
  def symlink?() false end
  ##% uid() -> Integer
  def uid() 0 end
  ##% writable?() -> Boolean
  def writable?() BOOLEAN end
  ##% writable_real?() -> Boolean
  def writable_real?() BOOLEAN end
  ##% zero?() -> Boolean
  def zero?() BOOLEAN end
end

module FileTest
  ##% self.blockdev?(String or IO) -> Boolean
  def self.blockdev?(path) BOOLEAN end
  ##% self.chardev?(String or IO) -> Boolean
  def self.chardev?(path) BOOLEAN end
  ##% self.directory?(String or IO) -> Boolean
  def self.directory?(path) BOOLEAN end
  ##% self.executable?(String or IO) ->Boolean
  def self.executable?(path) BOOLEAN end
  ##% self.executable_real?(String or IO) ->Boolean
  def self.executable_real?(path) BOOLEAN end
  ##% self.exist?(String or IO) -> Boolean
  def self.exist?(path) BOOLEAN end
  ##% self.exists?(String or IO) -> Boolean
  def self.exists?(path) BOOLEAN end
  ##% self.file?(String or IO) -> Boolean
  def self.file?(path) BOOLEAN end
  ##% self.grpowned?(String or IO) -> Boolean
  def self.grpowned?(filename) BOOLEAN end
  ##% self.identical?(String or IO, String or IO) -> Boolean
  def self.identical?(filename1, filename2) BOOLEAN end
  ##% self.owned?(String or IO) -> Boolean
  def self.owned?(path) BOOLEAN end  ##% self.setgid?(String or IO) -> Boolean
  ##% self.setgid?(String or IO) -> Boolean
  def self.setgid?(path) BOOLEAN end
  ##% self.setuid?(String or IO) -> Boolean
  def self.setuid?(path) BOOLEAN end
  ##% self.size(String or IO) -> Integer
  def self.size(path) 0 end
  ##% self.size?(String or IO) -> Boolean
  def self.size?(path) BOOLEAN end
  ##% self.socket?(String or IO) -> Boolean
  def self.socket?(path) BOOLEAN end
  ##% self.sticky?(String or IO) -> Boolean
  def self.sticky?(path) BOOLEAN end
  ##% self.writable?(String or IO) -> Boolean
  def self.writable?(path) BOOLEAN end
  ##% self.writable_real?(String or IO) -> Boolean
  def self.writable_real?(path) BOOLEAN end
  ##% self.zero?(String or IO) -> Boolean
  def self.zero?(path) BOOLEAN end
end

class Fixnum
  ### Numeric
  ##% +@() -> self
  def +@() self end
  ##% -@() -> Fixnum
  def -@() 0 end
  ##% "<=>"(other) -> Fixnum
  def <=>(other) 0 end
  ##% abs() -> Fixnum
  def abs() self end
  ##% ceil() -> Fixnum
  def ceil() 0 end
  ##% clone() -> self
  def clone() self end
  alias :dup :clone
  ##% coerce(Float) -> (Float, Float)
  ##% coerce(Numeric) -> (Fixnum, Fixnum)
  def coerce(other) [0.0, 0.0] end
  ##% div(Numeric) -> Fixnum
  def div(other) 0 end
  ##% divmod(Numeric) -> (Fixnum, Numeric)
  def divmod(other) [0, 0] end
  ##% eql?(Numeric) -> Boolean
  def eql?(other) BOOLEAN end
  ##% quo(Numeric) -> Float
  def quo(other) 0.0 end
  ##% fdiv(Numeric) -> Float
  def fdiv(other) 0.0 end
  ##% floor() -> Fixnum
  def floor() 0 end
  ##% integer?() -> Boolean
  def integer?() BOOLEAN end
  ##% modulo(Numeric) -> Fixnum
  def modulo(other) 0 end
  ##% nonzero?() -> self
  def nonzero?() self end
  ##% remainder(Numeric) -> Fixnum
  def remainder(other) 0 end
  ##% round() -> Fixnum
  def round() 0 end
  ##% step<a | a <= Numeric>(Numeric, ?a) {self or a or Fixnum -> ?} -> self
  ##% step<a | a <= Numeric>(Numeric, ?a) -> Enumerator<self, self or a or Fixnum>
  def step(limit, step = 1) self end
  ##% to_int() -> Fixnum
  def to_int() 0 end
  ##% truncate() -> Fixnum
  def truncate() 0 end
  ##% zero?() -> Boolean
  def zero?() BOOLEAN end

  ### Fixnum
  ##% id2name() -> String
  def id2name() '' end
  ##% to_sym() -> Symbol
  def to_sym() :a end
end

class Float
  DIG = 0
  EPSILONG = 0.0
  MANT_DIG = 0
  MAX = 0.0
  MAX_10_EXP = 0
  MAX_EXP = 0
  MIN = 0.0
  MIN_10_EXP = 0
  MIN_EXP = 0
  RADIX = 0
  ROUNDS = 0

  include Precision

  ### Precision
  ##% self.induced_from(a) -> Float
  def self.induced_from(number) 0.0 end
  ##% prec(Class) -> Float
  def prec(klass) 0.0 end

  ### Numeric
  ##% +@() -> self
  def +@() self end
  ##% -@() -> Float
  def -@() 0 end
  ##% "<=>"(other) -> Fixnum
  def <=>(other) 0 end
  ##% abs() -> Float
  def abs() self end
  ##% ceil() -> Integer
  def ceil() 0 end
  ##% clone() -> self
  def clone() self end
  alias :dup :clone
  ##% coerce(Numeric) -> (Float, Float)
  def coerce(other) [0.0, 0.0] end
  ##% div(Numeric) -> Float
  def div(other) 0.0 end
  ##% divmod(Numeric) -> (Float, Numeric)
  def divmod(other) [0, 0] end
  ##% eql?(Numeric) -> Boolean
  def eql?(other) BOOLEAN end
  ##% quo(Numeric) -> Float
  def quo(other) 0.0 end
  ##% fdiv(Numeric) -> Float
  def fdiv(other) 0.0 end
  ##% floor() -> Integer
  def floor() 0 end
  ##% integer?() -> Boolean
  def integer?() BOOLEAN end
  ##% modulo(Numeric) -> Float
  def modulo(other) 0.0 end
  ##% nonzero?() -> self
  def nonzero?() self end
  ##% remainder(Numeric) -> Float
  def remainder(other) 0.0 end
  ##% round() -> Fixnum
  def round() 0 end
  ##% step<a | a <= Numeric>(Numeric, ?a) {self or a or Fixnum -> ?} -> self
  ##% step<a | a <= Numeric>(Numeric, ?a) -> Enumerator<self, self or a or Fixnum>
  def step(limit, step = 1) self end
  ##% to_int() -> Integer
  def to_int() 0 end
  ##% truncate() -> Integer
  def truncate() 0 end
  ##% zero?() -> Boolean
  def zero?() BOOLEAN end

  ### Integer
  ##% %(Numeric) -> Float
  def %(other) 0.0 end
  ##% "*"(Numeric) -> Float
  def *(other) 0.0 end
  ##% "**"(Numeric) -> Float
  def **(other) 0.0 end
  ##% +(Numeric) -> Float
  def +(other) 0.0 end
  ##% -(Numeric) -> Float
  def -(other) 0.0 end
  ##% /(Numeric) -> Float
  def /(other) 0.0 end
  ##% finite?() -> Boolean
  def finite?() BOOLEAN end
  ##% hash() -> Fixnum
  def hash() 0 end
  ##% infinite?() -> Fixnum
  def infinite?() 0 end
  ##% nan?() -> Boolean
  def nan?() BOOLEAN end
  ##% to_f() -> self
  def to_f() self end
end

module GC
  ##% self.disable() -> Boolean
  def disable() BOOLEAN end
  ##% self.enable() -> Boolean
  def enable() BOOLEAN end
  ##% start() -> nil
  def start() end
  ##% stress() -> Boolean
  def stress() BOOLEAN end
  ##% stress=(Boolean) -> nil
  def stress=(value) end

  ##% garbase_collect() -> nil
  def garbase_collect() end
end

##% Hash<k, v, z | (k, v) <= t>
class Hash
  include Enumerable
  
  # FIXME
  ##% self.[](Hash<k', v', z'>) -> Hash<k', v', z'>
  ##% self.[](?k1, ?v1, ?k2, ?v2, ?k3, ?v3, ?k4, ?v4, ?k5, ?v5) -> Hash<k1 or k2 or k3 or k4 or k5, v1 or v2 or v3 or v4 or v5>
  def self.[](*) {} end
  # FIXME
  ##% self.new(?z') -> Hash<?, ?, z'>
  ##% self.new() {(k', v') -> ?} -> Hash<?, ?, ?>
  def self.new(*) {} end

  ##% ==(a) -> Boolean
  def ==(other) BOOLEAN end
  ##% ===(a) -> Boolean
  def ===(other) BOOLEAN end
  ##% eql?(a) -> Boolean
  def eql?(other) BOOLEAN end
  ##% [](a) -> v
  def [](key) _v end
  ##% []=<v' | v' <= v>(k, v') -> v'
  def []=(key, value) value end
  ##% store(k, v) -> v
  def store(key, value) value end
  # FIXME
  def clear() self end
  # FIXME
  ##% clone() -> Hash<k, v, z>
  def clone() self end
  alias :dup :clone
  ##% default(?a) -> d or a
  def default(key = nil) end
  ##% default=<d | d <= z>(d) -> d
  def default=(value) value end
  # FIXME
  def default_proc() end
  ##% delete(a) -> v
  ##% delete(a) {a -> b} -> b
  def delete(key) _v end
  ##% delete_if() -> Enumerator<self, (k, v)>
  ##% delete_if() {(k, v) -> ?} -> self
  def delete_if() self end
  alias :reject! :delete_if
  ##% each() {(k, v) -> ?} -> self
  ##% each() -> Enumerator<self, (k, v)>
  def each() yield _k, _v; self end
  alias :each_pair :each
  ##% each_key() {k -> ?} -> self
  ##% each_key() -> Enumerator<self, k>
  def each_key() yield _k; self end
  ##% each_value() {v -> ?} -> self
  ##% each_value() -> Enumerator<self, v>
  def each_value() yield _v; self end
  ##% empty?() -> Boolean
  def empty?() BOOLEAN end
  ##% equal?(a) -> Boolean
  def equal?(other) BOOLEAN end
  ##% fetch(a, ?b) -> v or b
  ##% fetch(a) {a -> b} -> v or b
  def fetch(key) _v end
  ##% has_key?(a) -> Boolean
  def has_key(key) BOOLEAN end
  alias :include? :has_key?
  alias :key? :has_key?
  alias :member? :has_key?
  ##% has_value?(a) -> Boolean
  def has_value?(value) BOOLEAN end
  alias :value? :has_value?
  ##% hash() -> Integer
  def hash() 0 end
  ##% index(a) -> k
  def index(val) _k end
  ##% indexes(*a) -> Array<v>
  def indexes(*index) [_v] end
  alias :indices :indexes
  ##% to_s() -> String
  def to_s() '' end
  ##% inspect() -> String
  def inspect() '' end
  ##% invert() -> Hash<v, k>
  def invert() {_v => _k} end
  ##% keys() -> Array<k>
  def keys() [_k] end
  ##% length() -> Integer
  def length() 0 end
  alias :size :length
  ##% merge<a | a <= Hash<k', v', z'> >(a) -> Hash<k or k', v or v', z or z'>
  ##% merge<a | a <= Hash<k', v', z'> >(a) {(k, v, v') -> v''} -> Hash<k or k', v'', z or z'>
  def merge(other) self end
  ##% merge!<a | a <= Hash<k, v, z> >(a) -> self
  ##% merge!<a | a <= Hash<k, v', z>, v'' <= v>(a) {(k, v, v') -> v''} -> self
  def merge!(other) self end
  ##% rehash() -> self
  def rehash() self end
  ##% reject() {(k, v) -> ?} -> Hash<k, v>
  ##% reject() -> Enumerator<Hash<k, v>, (k, v)> >
  def reject() self end
  ##% replace<a | a <= Hash<k, v, z> >(a) -> self
  def replace(other) self end
  ##% select() -> Enumerator<Array<(k, v)>, (k, v)>
  ##% select() {(k, v) -> ?} -> Array<(k, v)>
  def select() yield _k, _v; [_k, _v] end
  ##% shift() -> (k, v) or z
  def shift() [_k, _v] end
  ##% sort() -> Array<(k, v)>
  ##% sort() {(k, v) -> ?} -> Array<k, v>
  def sort() [[_k, _v]] end
  ##% to_a() -> Array<(k, v)>
  def to_a() [[_k, _v]] end
  ##% to_hash() -> self
  def to_hash() self end
  ##% update<a | a <= Hash<k, v, z> >(a) -> self
  ##% update<a | a <= Hash<k, v', z>, v'' <= v>(a) {(k, v, v') -> v''} -> self
  def update(other) self end
  ##% values() -> Array<v>
  def values() [_v] end
  ##% values_at(*a) -> Array<v or z>
  def values_at(*key) [_v] end

  private
  # Special methods for internal use
  ##% _k() -> k
  def _k() end
  ##% _v() -> v
  def _v() end
end

##% IO<| t <= String>
class IO
  SEEK_CUR = 0
  SEEK_END = 0
  SEEK_SET = 0

  include Enumerable

  ##% self.new(Integer, ?a) -> IO
  def self.new(fd, mode = 'r') IO.new(0) end
  ##% self.for_fd(Integer, ?a) -> IO
  def self.for_fd(fd, mode = 'r') IO.new(0) end
  ##% self.open(Integer, ?a) -> IO
  ##% self.open(Integer, ?a) {IO -> b} -> b
  def self.open(fd, mode = 'r') IO.new(0) end
  ##% self.foreach(String, ?String) {String -> ?} -> nil
  ##% self.foreach(String, ?String) -> Enumerator<nil, String>
  def self.foreach(path, rs = $/) yield ''; nil end
  ##% self.pipe() -> (IO, IO)
  def self.pipe() [IO.new(0), IO.new(0)] end
  ##% self.popen(String, ?a) -> IO
  ##% self.popen(String, ?a) {IO -> b} -> b
  def self.popen(command, mode = 'r') IO.new(0) end
  ##% self.read(String, ?Integer, ?Integer) -> String
  def self.read(path, length = nil, offset = 0) '' end
  ##% self.readlines(String, ?String) -> Array<String>
  def self.readlines(path, rs = $/) [''] end
  ##% self.select(Array<IO>, ?Array<IO>, ?Array<IO>, ?Integer) -> (Array<IO>, Array<IO>, Array<IO>)
  def self.select(reads, writes = [], excepts = [], timeout = nil) [[IO.new(0)], [IO.new(0)], [IO.new(0)]] end
  ##% self.sysopen(String, ?a, ?Integer) -> Integer
  def self.sysopen(path, mode = 'r', perm = 0666) 0 end
  
  # FIXME to_s
  ##% "<<"(a) -> self
  def <<(object) self end
  ##% binmode() -> self
  def binmode() self end
  ##% bytes() -> Enumerator<self, Fixnum>
  def bytes() Enumerator.new end
  ##% each_char() {Fixnum -> ?} -> self
  ##% each_char() -> Enumerator<self, Fixnum>
  def each_char() yield 0; self end
  alias :chars :each_char
  ##% clone() -> IO
  def clone() self end
  alias :dup :clone
  ##% close() -> nil
  def close() end
  ##% close_read() -> nil
  def close_read() end
  ##% close_write() -> nil
  def close_write() end
  ##% closed?() -> Boolean
  def closed?() BOOLEAN end
  ##% each(?String) {String -> ?} -> self
  ##% each(?String) -> Enumerator<self, String>
  def each(rs = $/) yield ''; self end
  alias :each_line :each
  ##% each_byte() {Fixnum -> ?} -> self
  ##% each_byte() -> Enumerator<self, Fixnum>
  def each_byte() yield 0; self end
  ##% eof() -> Boolean
  def eof() BOOLEAN end
  alias :eof? :eof
  ##% fcntl(Integer, ?(Integer or String or Boolean)) -> Integer
  def fcntl(cmd, arg = 0) 0 end
  ##% fileno() -> Integer
  def fileno() 0 end
  alias :to_i :fileno
  ##% flush() -> self
  def flush() self end
  ##% fsync() -> Integer
  def fsync() 0 end
  ##% getbyte() -> Fixnum
  def getbyte() 0 end
  ##% getc() -> Fixnum
  def getc() 0 end
  ##% gets(?String) -> String
  def gets(rs = $/) '' end
  ##% ioctl(Integer, ?(Integer or String or Boolean)) -> Integer
  def ioctl(cmd, arg = 0) 0 end
  ##% isatty() -> Boolean
  def isatty() BOOLEAN end
  alias :tty? :isatty
  ##% lineno() -> Integer
  def lineno() 0 end
  ##% lineno=(Integer) -> nil
  def lineno=(number) end
  ##% lines(?String) -> Enumerator<self, String>
  def lines(rs = $/) Enumerator.new end
  ##% pid() -> Integer
  def pid() 0 end
  ##% pos() -> Integer
  alias :tell :pos
  ##% pos=(Integer) -> Integer
  def pos=(n) end
  ##% print(*a) -> nil
  def print(*arg) end
  ##% printf(String, *a) -> nil
  def printf(format, *arg) end
  ##% putc<c | c <= Integer>(c) -> c
  def putc(ch) ch end
  ##% puts(*a) -> nil
  def puts(*obj) end
  ##% read(?Integer, ?String) -> String
  def read(length = nil, outbuf = '') '' end
  ##% read_nonblock(Integer, ?String) -> String
  def read_nonblock(maxlen, outbuf = '') '' end
  ##% readbyte() -> Fixnum
  def readbyte() 0 end
  ##% readchar() -> Fixnum
  def readchar() 0 end
  ##% readline(?String) -> String
  def readline(rs = $/) '' end
  ##% readlines(?String) -> Array<String>
  def readlines(rs = $/) [''] end
  ##% readpartial(?Integer, ?String) -> String
  def readpartial(maxlen, outbuf = '') '' end
  ##% reopen(IO) -> self
  ##% reopen(String, ?a) -> self
  def reopen(*) self end
  ##% rewind() -> Integer
  def rewind() 0 end
  ##% seek(Integer, ?Fixnum) -> Fixnum
  def seek(offset, whence = IO::SEEK_SET) 0 end
  ##% stat() -> File::Stat
  def stat() File::Stat.new('') end
  ##% sync() -> Boolean
  def sync() BOOLEAN end
  ##% sync=(Boolean) -> Boolean
  def sync=(newstate) newstate end
  ##% sysread(Integer, ?String) -> String
  def sysread(maxlen, outbuf = '') '' end
  ##% sysseek(Integer, ?Fixnum) -> Fixnum
  def sysseek(offset, whence = IO::SEEK_SET) 0 end
  # FIXME to_s
  ##% syswrite(a) -> Integer
  def syswrite(string) 0 end
  ##% to_io() -> self
  def to_io() self end
  ##% ungetc(a) -> nil
  def ungetc(char) end
  # FIXME to_s
  ##% write(a) -> Integer
  def write(str) 0 end
  # FIXME to_s
  ##% write_nonblock(a) -> Integer
  def write_nonblock(str) 0 end
end

class Integer
  include Precision

  ### Precision
  ##% self.induced_from(a) -> Fixnum
  def self.induced_from(number) 0 end
  ##% prec(Class) -> Fixnum
  def prec(klass) 0 end

  ### Numeric
  ##% +@() -> self
  def +@() self end
  ##% -@() -> Integer
  def -@() 0 end
  ##% "<=>"(other) -> Fixnum
  def <=>(other) 0 end
  ##% abs() -> Integer
  def abs() self end
  ##% ceil() -> Integer
  def ceil() 0 end
  ##% clone() -> self
  def clone() self end
  alias :dup :clone
  ##% coerce(Float) -> (Float, Float)
  ##% coerce(Numeric) -> (Integer, Integer)
  def coerce(other) [0.0, 0.0] end
  ##% div(Numeric) -> Integer
  def div(other) 0 end
  ##% divmod(Numeric) -> (Integer, Integer)
  def divmod(other) [0, 0] end
  ##% eql?(Numeric) -> Boolean
  def eql?(other) BOOLEAN end
  ##% quo(Numeric) -> Float
  def quo(other) 0.0 end
  ##% fdiv(Numeric) -> Float
  def fdiv(other) 0.0 end
  ##% floor() -> Integer
  def floor() 0 end
  ##% integer?() -> Boolean
  def integer?() BOOLEAN end
  ##% modulo(Numeric) -> Integer
  def modulo(other) 0 end
  ##% nonzero?() -> self
  def nonzero?() self end
  ##% remainder(Numeric) -> Integer
  def remainder(other) 0 end
  ##% round() -> Integer
  def round() 0 end
  ##% step<a | a <= Numeric>(Numeric, ?a) {self or a or Fixnum -> ?} -> self
  ##% step<a | a <= Numeric>(Numeric, ?a) -> Enumerable::Enumerator<self, self or a or Fixnum>
  def step(limit, step = 1) self end
  ##% to_int() -> Integer
  def to_int() 0 end
  ##% truncate() -> Integer
  def truncate() 0 end
  ##% zero?() -> Boolean
  def zero?() BOOLEAN end

  ### Integer
  ##% %(Numeric) -> Fixnum or Bignum
  def %(other) 0 end
  ##% &(Numeric) -> Fixnum or Bignum
  def &(other) 0 end
  ##% "*"(Float) -> Float
  ##% "*"(Integer) -> Fixnum or Bignum
  def *(other) 0 end
  ##% "**"(Float) -> Float
  ##% "**"(Integer) -> Fixnum or Bignum
  def **(other) 0 end
  ##% +(Float) -> Float
  ##% +(Integer) -> Fixnum or Bignum
  def +(other) 0 end
  ##% -(Float) -> Float
  ##% -(Integer) -> Fixnum or Bignum
  def -(other) 0 end
  ##% /(Float) -> Float
  ##% /(Integer) -> Fixnum or Bignum
  def /(other) 0 end
  ##% ">>"(Integer) -> Fixnum or Bignum
  def >>(bits) 0 end
  ##% [](Integer) -> Fixnum
  def [](nth) 0 end
  ##% ^(Integer) -> Fixnum or Bignum
  def ^(other) 0 end
  ##% chr() -> String
  def chr() '' end
  ##% downto(Numeric) {self -> ?} -> self
  ##% downto(Numeric) -> Enumerator<self, self>
  def downto(limit, step = 1) self end
  ##% even?() -> Boolean
  def even?() BOOLEAN end
  ##% integer?() -> TrueClass
  def integer?() true end
  ##% next() -> Fixnum or Bignum
  def next() 0 end
  alias :succ :next
  ##% odd?() -> Boolean
  def odd?() BOOLEAN end
  ##% ord() -> self
  def ord() self end
  ##% pred() -> Fixnum or Bignum
  def pred() 0 end
  ##% size() -> Fixnum
  def size() 0 end
  ##% times() {self -> ?} -> self
  ##% times() -> Enumerator<self, self>
  def times() yield self end
  ##% to_f() -> Float
  def to_f() 0.0 end
  ##% to_i() -> self
  def to_i() self end
  alias :to_int :to_i
  ##% to_s(?Integer) -> String
  def to_s(base = nil) '' end
  ##% upto(Numeric) {self -> ?} -> self
  ##% upto(Numeric) -> Enumerator<self, self>
  def upto(limit, step = 1) self end
  ##% "|"(Numeric) -> Fixnum or Bignum
  def |(other) 0 end
  ##% ~() -> Fixnum or Bignum
  def ~() 0 end
end

module Kernel
  private

  ### Constants
  ARGF = IO.new(0)
  ARGV = ['']
  DATA = File.new('')
  ENV = Hash['', '']
  FALSE = false
  NIL = nil
  PLATFORM = ''
  RELEASE_DATE = ''
  RUBY_COPYRIGHT = ''
  RUBY_DESCRIPTION = ''
  RUBY_PATCHLEVEL = 0
  RUBY_PLATFORM = ''
  RUBY_RELEASE_DATE = ''
  RUBY_VERSION = ''
  SCRIPT_LINES__ = {}
  STDERR = IO.new(0)
  STDIN = IO.new(0)
  STDOUT = IO.new(0)
  TOPLEVEL_BINDING = Binding.new
  TRUE = true
  VERSION = ''

  ### Global Variables
  $! = Exception.new
  $" = ['']
  $LOADED_FEATURES = $"
  $$ = 0
  #$& = ''
  #$' = ''
  $* = ['']
  #$+ = ''
  $, = ''
  $/ = ''
  $-0 = ''
  $; = // || ''
  $-F = $;
  $: = ['']
  $LOAD_PATH = $:
  $-I = $:
  $KCODE = ''
  $-K = ''
  $-a = BOOLEAN
  $DEBUG = BOOLEAN
  $-d = BOOLEAN
  $-i = BOOLEAN
  $-l = BOOLEAN
  $-p = BOOLEAN
  $VERBOSE = BOOLEAN
  $-v = BOOLEAN
  $-w = BOOLEAN
  $. = 0
  $0 = ''
  $PROGRAM_NAME = ''
  #$1 = ''
  #$2 = ''
  #$3 = ''
  #$4 = ''
  #$5 = ''
  #$6 = ''
  #$7 = ''
  #$8 = ''
  #$9 = ''
  #$10 = ''
  #$11 = ''
  $< = IO.new(0)
  $= = BOOLEAN
  $> = IO.new(0)
  $stdout = $>
  $defout = $stdout
  $? = Process::Status.new
  $@ = ['']
  $FILENAME = ''
  $SAFE = 0
  $\ = ''
  $_ = ''
  #$` = ''
  $stderr = IO.new(0)
  $deferr = $stderr
  $stdin = IO.new(0)
  $~ = MatchData
  
  ### Module Methods
  # FIXME to_ary
  ##% Array<a | a <= Array>(a) -> a
  ##% Array(a) -> a
  def Array(arg) [] end
  ##% Float(a) -> Float
  def Float(arg) 0.0 end
  ##% Integer(a) -> Integer
  def Integer(arg) 0 end
  ##% String(a) -> String
  def String(arg) '' end
  ##% __method__() -> Symbol
  def __method__() :a end
  ##% `(command) -> String
  def `(command) '' end         # `
  ##% abort(?String) -> nil
  def abort(message = $!.message) end
  ##% at_exit() {() -> ?} -> Proc
  def at_exit() Proc.new end
  ##% autoload(String or Symbol, String) -> nil
  def autoload(const_name, feature) end
  ##% autoload?(String or Symbol) -> String
  def autoload?(const_name) '' end
  ##% binding() -> Binding
  def binding() Binding.new end
  ##% block_given?() -> Boolean
  def block_given() BOOLEAN end
  alias :iterator? :block_given?
  # FIXME
  ##% callcc() {Continuation -> ?} -> ?
  def callcc() end
  ##% caller(?Integer) -> Array<String>
  def caller(level_num = 1) [''] end
  # FIXME
  ##% catch(a) {a -> ?} -> ?
  def catch(tag) yield tag end
  ##% chomp(?String) -> String
  def chomp(rs = $/) '' end
  ##% chomp!(?String) -> String
  def chomp!(rs = $/) '' end
  ##% chop() -> String
  def chop() '' end
  ##% chop!() -> String
  def chop() '' end
  ##% eval(String, ?Proc or Binding, ?String, ?Integer) -> ?
  def eval(expr, bind = nil, fname = __FILE__, lineno = 1) end
  ##% exec(String) -> nil
  ##% exec(String, *String) -> nil
  def exec(command) end
  ##% exit(Integer or Boolean) -> nil
  def exit(status = true) end
  ##% exit!(Integer or Boolean) -> nil
  def exit!(status = true) end
  # FIXME
  ##% raise() -> nil
  ##% raise(String) -> nil
  ##% raise(?, ?String, Array<String>) -> nil
  def raise(*) end
  alias :fail :raise
  ##% fork() -> Integer
  ##% fork() {() -> ?} -> Integer
  def fork() yield end
  ##% sprintf(String, *a) -> String
  def sprintf(format, *arg) '' end
  alias :format :sprintf
  ##% getc() -> Fixnum
  def getc() 0 end
  ##% gets(?String) -> String
  def gets(rs = $/) '' end
  ##% global_variables() -> Array<String>
  def global_variables() [''] end
  ##% gsub(String or Regexp, String) -> String
  ##% gsub(String or Regexp) {String -> ?} -> String
  ##% gsub(String or Regexp) -> Enumerator<String, String>
  def gsub(pattern, replace = nil) '' end
  ##% gsub!(String or Regexp, String) -> self
  ##% gsub!(String or Regexp) {String -> ?} -> self
  ##% gsub!(String or Regexp) -> Enumerator<String, self>
  def gsub!(pattern, replace = nil) self end
  # skelton
  def proc() Proc.new end
  alias :lambda :proc
  ##% load(String, ?Boolean) -> TrueClass
  def load(file, priv = false) true end
  ##% local_varaibles() -> Array<String>
  def local_varaibles() [''] end
  ##% loop() {() -> a} -> a
  def loop() yield end
  ##% open(String or Integer, ?a, ?Integer) -> IO
  ##% open(String or Integer, ?a, ?Integer) {IO -> ?} -> nil
  def open(name, mode = 'r', perm = nil) IO.new(0) end
  ##% p(*a) -> nil
  def p(*arg) end
  ##% print(*a) -> nil
  def p(*arg) end
  ##% printf(String, *a) -> nil
  ##% printf(IO, String, *a) -> nil
  def printf(*) end
  ##% putc<a | a <= Integer or String>(a) -> a
  def putc(ch) ch end
  ##% puts(*a) -> nil
  def puts(*arg) end
  ##% rand(?Numeric) -> Integer or Float
  def rand(max = 0) 0 || 0.0 end
  ##% readline(?String) -> String
  def readline(rs = $/) '' end
  ##% readlines(?String) -> Array<String>
  def readlines(rs = $/) [''] end
  ##% require(String) -> Boolean
  def require(feature) BOOLEAN end
  ##% require_relative(String) -> Boolean
  def require_relative(feature) BOOLEAN end
  ##% scan(String or Regexp) -> Array<String> or Array<Array<String> >
  ##% scan(String or Regexp) {String -> ?} -> self
  def scan(re) [''] || [['']] end
  ##% select(Array<IO>, ?Array<IO>, ?Array<IO>, ?Integer) -> (Array<IO>, Array<IO>, Array<IO>)
  def select(reads, writes = [], excepts = [], timeout = nil) [[IO.new(0)], [IO.new(0)], [IO.new(0)]] end
  def set_trace_func(*) end
  ##% sleep(?Numeric) -> Integer
  def sleep(sec = 0) 0 end
  ##% split(?(String or Regexp), ?Integer) -> Array<String> or Array<Array<String> >
  def split(sep = $;, limit = 0) [''] || [['']] end
  ##% srand(?a) -> Integer
  def srand(seed = nil) 0 end
  ##% sub(String or Regexp, String) -> String
  ##% sub(String or Regexp) {String -> ?} -> String
  ##% sub(String or Regexp) -> Enumerator<String, String>
  def sub(pattern, replace = nil) '' end
  ##% sub!(String or Regexp, String) -> self
  ##% sub!(String or Regexp) {String -> ?} -> self
  ##% sub!(String or Regexp) -> Enumerator<String, self>
  def sub!(pattern, replace = nil) self end
  ##% syscall(Integer, *(String or Integer)) -> Integer
  def syscall(num, *arg) 0 end
  ##% system(String) -> Boolean
  ##% system(String, *String) -> Boolean
  def system(*) BOOLEAN end
  ##% test(String or Integer, File) -> Boolean or Time or Integer
  ##% test(String or Integer, File, File) -> Boolean
  def test(cmd, file1, file2 = nil) BOOLEAN or Time.new or 0 end
  # FIXME
  ##% throw(a, ?b) -> nil
  def throw(tag, value = nil) end
  def trace_var(*) end
  ##% trap(String or Symbol or Integer, String or Process) -> ?
  ##% trap(String or Symbol or Integer) {() -> ?} -> ?
  def trap(signal, command = nil) end
  def untrace_var(*) end
  ##% warn(a) -> nil
  def warn(message) end
end

module Marshal
  MAJOR_VERSION = 0
  MINOR_VERSION = 0

  ##% dump(Object, ?(IO or String), ?Integer) -> IO or String
  def dump(obj, port = '', limit = -1) IO.new(0) || '' end
  ##% load(IO or String, ?Proc) -> Object
  def load(port, proc = nil) Object.new end
end

class MatchData
  ##% [](Integer) -> String
  ##% [](Range) -> String
  ##% [](Integer, Integer) -> Array<String>
  def [](*) '' end
  ##% begin(Integer) -> Fixnum
  def begin(n) 0 end
  ##% captures() -> Array<String>
  def captures() [''] end
  ##% end(Integer) -> Fixnum
  def end(n) 0 end
  ##% end(Integer) -> Fixnum
  def end(n) 0 end
  ##% length() -> Fixnum
  def length() 0 end
  alias :size :length
  ##% offset(Integer) -> Fixnum
  def offset(n) 0 end
  ##% post_match() -> String
  def post_match() '' end
  ##% pre_match() -> String
  def pre_match() '' end
  ##% select() {Strign -> ?} -> Array<String>
  def select() yield ''; [''] end
  ##% string() -> String
  def string() '' end
  ##% to_a() -> Array<String>
  def to_a() [''] end
  ##% to_s() -> String
  def to_s() '' end
  ##% values_at(*Integer) -> Array<String>
  def values_at(*idnex) [''] end
end

module Math
  E = 0.0
  PI = 0.0

  module_function
  
  ##% acos(Integer or Float) -> Float
  def acos(x) 0.0 end
  ##% acosh(Integer or Float) -> Float
  def acosh(x) 0.0 end
  ##% asin(Integer or Float) -> Float
  def asin(x) 0.0 end
  ##% asinh(Integer or Float) -> Float
  def asinh(x) 0.0 end
  ##% atan(Integer or Float) -> Float
  def atan(x) 0.0 end
  ##% atan2(Integer or Float) -> Float
  def atan2(x) 0.0 end
  ##% atanh(Integer or Float) -> Float
  def atanh(x) 0.0 end
  ##% cos(Integer or Float) -> Float
  def cos(x) 0.0 end
  ##% cosh(Integer or Float) -> Float
  def cosh(x) 0.0 end
  ##% erf(Integer or Float) -> Float
  def erf(x) 0.0 end
  ##% erfc(Integer or Float) -> Float
  def erfc(x) 0.0 end
  ##% exp(Integer or Float) -> Float
  def exp(x) 0.0 end
  ##% frexp(Integer or Float) -> (Float, Fixnum)
  def frexp(x) [0.0, 0] end
  ##% hypot(Integer or Float, Integer or Float) -> Float
  def hypot(x, y) 0.0 end
  ##% ldexp(Integer or Float, Integer) -> Float
  def ldexp(x, exp) 0.0 end
  ##% log(Integer or Float) -> Float
  def log(x) 0.0 end
  ##% log10(Integer or Float) -> Float
  def log10(x) 0.0 end
  ##% sin(Integer or Float) -> Float
  def sin(x) 0.0 end
  ##% sinh(Integer or Float) -> Float
  def sinh(x) 0.0 end
  ##% sqrt(Integer or Float) -> Float
  def sqrt(x) 0.0 end
  ##% tan(Integer or Float) -> Float
  def tan(x) 0.0 end
  ##% tanh(Integer or Float) -> Float
  def tanh(x) 0.0 end
end

class Method
  # FIXME
end

class Module
  ##% self.constants() -> Array<String>
  def self.constants() [''] end
  ##% self.nesting() -> Array<Module or Class>
  def self.nesting() [Module.new || Class.new] end
  ##% self.new() -> Module
  ##% self.new() {Module -> ?} -> Module
  def self.new() super end

  ##% "<"(Module) -> Boolean
  def <(other) BOOLEAN end
  ##% "<="(Module) -> Boolean
  def <=(other) BOOLEAN end
  ##% "<=>"(Module) -> Integer
  def <=>(other) 0 end
  ##% ===(other) -> Boolean
  def ===(other) BOOLEAN end
  ##% ">"(Module) -> Boolean
  def >(other) BOOLEAN end
  ##% ">="(Module) -> Boolean
  def >=(other) BOOLEAN end
  ##% ancestors() -> Array<Module or Class>
  def ancestors() [Module.new || Class.new] end
  ##% autoload(String or Symbol, String) -> nil
  def autoload(const_name, feature) nil end
  ##% autoload(String or Symbol) -> String
  def autoload?(const_name) '' end
  ##% module_eval(String, ?String, ?Integer) -> Object
  ##% module_eval() {self -> ?} -> Object
  def module_eval(*) end
  alias :class_eval :module_eval
  ##% class_variable_defined?(String or Symbol) -> Boolean
  def class_variable_defined?(name) BOOLEAN end
  ##% class_variables() -> Array<String>
  def class_variables() [''] end
  ##% const_defined?(String or Symbol) -> Boolean
  def const_defined?(name) BOOLEAN end
  ##% const_get(String or Symbol) -> Object
  def const_get(name) Object.new end
  ##% const_missing(Symbol) -> nil
  def const_missing(name) end
  ##% const_set(String or Symbol, a) -> a
  def const_set(name, value) value end
  ##% constants() -> Array<String>
  def constants() [''] end
  ##% include?(Module) -> Boolean
  def include?() BOOLEAN end
  ##% included_modules() -> Array<Module>
  def included_modules() [Module.new] end
  ##% instance_method(String or Symbol) -> UnboundMethod
  def instance_method(name) UnboundMethod.new end
  ##% instance_methods(?Boolean) -> Array<String>
  def instance_methods(inherited_too = true) [''] end
  ##% method_defined(String or Symbol) -> Boolean
  def method_defined(name) BOOLEAN end
  ##% name() -> String
  def name() '' end
  alias :to_s :name
  ##% private_class_method(*(String or Symbol)) -> self
  def private_class_method(*name) self end
  ##% private_instance_methods(?Boolean) -> Array<String>
  def private_instance_methods(inherited_too = true) [''] end
  ##% private_method_defined?(String or Symbol) -> Boolean
  def private_method_defined?(name) BOOLEAN end
  ##% protected_class_method(*(String or Symbol)) -> self
  def protected_class_method(*name) self end
  ##% protected_instance_methods(?Boolean) -> Array<String>
  def protected_instance_methods(inherited_too = true) [''] end
  ##% protected_method_defined?(String or Symbol) -> Boolean
  def protected_method_defined?(name) BOOLEAN end
  ##% public_class_method(*(String or Symbol)) -> self
  def public_class_method(*name) self end
  ##% public_instance_methods(?Boolean) -> Array<String>
  def public_instance_methods(inherited_too = true) [''] end
  ##% public_method_defined?(String or Symbol) -> Boolean
  def public_method_defined?(name) BOOLEAN end
    
  private
  ##% alias_method(String or Symbol, String or Symbol) -> self
  def alias_method(new, original) self end
  ##% append_features(Module) -> self
  def append_features(module_or_class) self end
  ##% attr(String or Symbol, ?Boolean) -> nil
  def attr(name, assignable = false) nil end
  ##% attr_accessor(*(String or Symbol)) -> nil
  def attr_accessor(*name) nil end
  ##% attr_reader(*(String or Symbol)) -> nil
  def attr_reader(*name) nil end
  ##% attr_writer(*(String or Symbol)) -> nil
  def attr_writer(*name) nil end
  ##% module_exec(*args) {args -> ?} -> Object
  def module_exec(*args) yield *args end
  alias :class_exec :module_exec
  ##% class_variable_get(String or Symbol) -> Object
  def class_variable_get(name) Object.new end
  ##% class_variable_set(String or Symbol, a) -> a
  def class_variable_set(name, val) val end
  ##% define_method<a | a <= Proc or Method or UnboundMethod>(String or Symbol, a) -> Proc or Method or UnboundMethod
  ##^ define_method(String or Symbol) {() -> ?}  -> Proc
  def define_method(*) end
  ##% extend_object<a | a <= Module>(a) -> a
  def extend_object(mod) mod end
  ##% extended(Module) -> nil
  def extended(class_or_module) end
  ##% include(*Module) -> self
  def include(*mod) self end
  ##% included(Module) -> nil
  def included(class_or_module) end
  ##% method_added(Symbol) -> nil
  def method_added(name) end
  ##% method_removed(Symbol) -> nil
  def method_removed(name) end
  ##% method_undefined(Symbol) -> nil
  def method_undefined(name) end
  ##% module_function(*(String or Symbol)) -> self
  def module_function(*name) self end
  ##% private(*(String or Symbol)) -> self
  def private(*name) self end
  ##% protected(*(String or Symbol)) -> self
  def protected(*name) self end
  ##% public(*(String or Symbol)) -> self
  def public(*name) self end
  ##% remove_class_variable(String or Symbol) -> Object
  def remove_class_variable(name) Object.new end
  ##% remove_const(String or Symbol) -> Object
  def remove_const(name) Object.new end
  ##% remove_method(*(String or Symbol)) -> self
  def remove_method(*name) self end
  ##% undef_method(*(String or Symbol)) -> self
  def undef_method(*name) self end
end

class NilClass
  ##% &(a) -> FalseClass
  def &(other) false end
  ##% ^(a) -> Boolean
  def ^(other) BOOLEAN end
  ##% nil?() -> TrueClass
  def nil?() true end
  ##% to_a() -> Array
  def to_a() [] end
  ##% to_f() -> Float
  def to_f() 0.0 end
  ##% to_i() -> Fixnum
  def to_i() 0 end
  ##% to_s() -> String
  def to_s() '' end
  ##% "|"(a) -> Boolean
  def |(other) BOOLEAN end
end

class Numeric
  include Comparable

  ### Comparable
  ##% "<"(Numeric) -> Boolean
  def <(other) BOOLEAN end
  ##% "<="(Numeric) -> Boolean
  def <=(other) BOOLEAN end
  ##% ==(Numeric) -> Boolean
  def ==(other) BOOLEAN end
  ##% ">"(Numeric) -> Boolean
  def >(other) BOOLEAN end
  ##% ">="(Numeric) -> Boolean
  def >=(other) BOOLEAN end
  ##% between?(Numeric, Numeric) -> Boolean
  def between?(min, max) BOOLEAN end

  ### Numeric
  ##% +@() -> self
  def +@() self end
  ##% -@() -> Numeric
  def -@() Numeric end
  ##% "<=>"(Numeric) -> Fixnum
  def <=>(other) 0 end
  ##% abs() -> Numeric
  def abs() self end
  ##% ceil() -> Integer
  def ceil() 0 end
  ##% clone() -> self
  def clone() self end
  alias :dup :clone
  ##% coerce(Numeric) -> (Float, Float)
  def coerce(other) [0.0, 0.0] end
  ##% div(Numeric) -> Integer
  def div(other) 0 end
  ##% divmod(Numeric) -> (Integer, Numeric)
  def divmod(other) [0, 0] end
  ##% eql?(Numeric) -> Boolean
  def eql?(other) BOOLEAN end
  ##% quo(Numeric) -> Float
  def quo(other) 0.0 end
  ##% fdiv(Numeric) -> Float
  def fdiv(other) 0.0 end
  ##% floor() -> Integer
  def floor() 0 end
  ##% integer?() -> Boolean
  def integer?() BOOLEAN end
  ##% modulo(Numeric) -> Numeric
  def modulo(other) 0 end
  ##% nonzero?() -> self
  def nonzero?() self end
  ##% remainder(Numeric) -> Numeric
  def remainder(other) 0 end
  ##% round() -> Integer
  def round() 0 end
  ##% step<a | a <= Numeric>(Numeric, ?a) {self or a or Fixnum -> ?} -> self
  ##% step<a | a <= Numeric>(Numeric, ?a) -> Enumerable::Enumerator<self, self or a or Fixnum>
  def step(limit, step = 1) self end
  ##% to_int() -> Integer
  def to_int() 0 end
  ##% truncate() -> Integer
  def truncate() 0 end
  ##% zero?() -> Boolean
  def zero?() BOOLEAN end
end

class Object
  ##% ==(a) -> Boolean
  def ==(other) BOOLEAN end
  ##% ===(a) -> Boolean
  def ===(other) BOOLEAN end
  ##% =~(a) -> FalseClass
  def =~(other) FALSE end
  ##% __id__() -> Integer
  def __id__() 0 end
  alias :object_id :__id__
  alias :id :__id__
  ##% send(String or Symbol, *a) -> Object
  def send(name, *args) end
  ##% _dump(Integer) -> String
  def _dump(limit) '' end
  ###% class() -> Class
  #def class() Class.new end
  alias :type :class
  ##% clone() -> self
  def clone() self end
  alias :dup :clone
  ##% display(?IO) -> nil
  def display(out = $stdout) nil end
  ##% to_enum(?String, *a) -> Enumerator
  def to_enum(method = :each, *args) Enumerator.new end
  alias :enum_for :to_enum
  ##% eql?(a) -> Boolean
  def eql?(other) BOOLEAN end
  ##% equal?(a) -> Boolean
  def equal?(other) BOOLEAN end
  ##% extend(*Module) -> self
  def extend(*modules) self end
  ##% freeze() -> self
  def freeze() self end
  ##% frozen?() -> Boolean
  def frozen?() BOOLEAN end
  ##% hash() -> Fixnum
  def hash() 0 end
  ##% inspect() -> String
  def inspect() '' end
  ##% instance_eval(String, ?String, ?Integer) -> Object
  ##% instance_eval() {Object -> ?} -> Object
  def instance_eval(expr, filename = "(eval)", lineno = 1) Object.new end
  ##% instance_of?(Class) -> Boolean
  def instance_of?(klass) BOOLEAN end
  ##% instance_variable_defined?(String or Symbol) -> Boolean
  def instance_variable_defined?(var) BOOLEAN end
  ##% instance_variable_get(String or Symbol) -> Object
  def instance_variable_get(var) Object.new end
  ##% instance_variable_set(String or Symbol, a) -> a
  def instance_variable_set(var, value) value end
  ##% instance_variables() -> Array<String>
  def instance_variables() [''] end
  ##% is_a?(Module) -> Boolean
  def is_a?(mod) BOOLEAN end
  alias :kind_of? :is_a?
  ##% marshal_dump() -> Object
  def marshal_dump() Object.new end
  ##% marshal_load(Object) -> ?
  def marshal_load(obj) end
  ##% method(String or Symbol) -> Method
  def method(name) Method.new end
  def method_missing(name, *args) end
  ##% methods(?Boolean) -> Array<String>
  def methods(include_inherited = true) [''] end
  ##% nil?() -> Boolean
  def nil?() BOOLEAN end
  ##% private_methods(?Boolean) -> Array<String>
  def private_methods(include_inherited = true) [''] end
  ##% protected_methods(?Boolean) -> Array<String>
  def protected_methods(include_inherited = true) [''] end
  ##% public_methods(?Boolean) -> Array<String>
  def public_methods(include_inherited = true) [''] end
  ##% respond_to?(String or Symbol, ?Boolean) -> Boolean
  def respond_to?(name, include_private = false) BOOLEAN end
  ##% singleton_methods(?Boolean) -> Array<String>
  def singleton_methods(inherited_too = true) [''] end
  ##% taint() -> self
  def taint() self end
  ##% tainted?() -> Boolean
  def tainted?() BOOLEAN end
  ##% tap() {self -> ?} -> self
  def tap() yield self; self end
  ##% to_a() -> Array
  def to_a() [] end
  ##% to_s() -> String
  def to_s() '' end
  ##% untaint() -> self
  def untaint() self end

  private
  ##% to_ary() -> Array
  def to_ary() [] end
  ##% to_hash() -> Hash
  def to_hash() {} end
  ##% to_int() -> Integer
  def to_int() 0 end
  ##% to_io() -> IO
  def to_io() IO.new(0) end
  ##% to_proc() -> Proc
  def to_proc() Proc.new end
  ##% to_regexp() -> Regexp
  def to_regexp() Regexp.new end
  ##% to_str() -> String
  def to_str() '' end

  # FIXME singleton methods
end

module ObjectSpace
  # FIXME
end

module Precision
  ##% self.included(Module or Class) -> Precision
  def self.included(module_or_class) self end
  ##% self.induced_from(a) -> Object
  def self.induced_from(number) 0 end

  ##% prec(Class) -> Fixnum
  def prec(klass) 0 end
  ##% prec_f() -> Float
  def prec_f() 0.0 end
  ##% prec_i() -> Fixnum
  def prec_i() 0 end
end

class Proc
  ##% call(*a) -> Object
  def call(*arg) Object.new end
  alias :[] :call
  ##% arity() -> Fixnum
  def arity() 0 end
  ##% binding() -> Binding
  def binding() Binding.new end
  ##% to_proc() -> self
  def to_proc() self end
  ##% to_s() -> String
  def to_s() '' end
end

module Process
  PRIO_PGRP       =  0
  PRIO_PROCESS    =  0
  PRIO_USER       =  0
  RLIMIT_AS       =  0
  RLIMIT_CORE     =  0
  RLIMIT_CPU      =  0
  RLIMIT_DATA     =  0
  RLIMIT_FSIZE    =  0
  RLIMIT_MEMLOCK  =  0
  RLIMIT_NOFILE   =  0
  RLIMIT_NPROC    =  0
  RLIMIT_RSS      =  0
  RLIMIT_SBSIZE   =  0
  RLIMIT_STACK    =  0
  RLIM_INFINITY   =  0
  RLIM_SAVED_CUR  =  0
  RLIM_SAVED_MAX  =  0
  WNOHANG         =  0
  WUNTRACED       =  0
  
  ##% self.abort(?String) -> ?
  def self.abort(message = '') end
  ##% self.detach(Integer) -> Thread
  def self.detach(pid) Thread.current end
  ##% self.exec(String, *String) -> ?
  def self.exec(command, *args) end
  ##% self.exit(?Boolean) -> ?
  def self.exit(status = true) end
  ##% self.exit!(?Boolean) -> ?
  def self.exit!(status = true) end
  ##% self.fork() -> Integer
  ##% self.fork() {() -> ?} -> Integer
  def self.fork() yield; 0 end

  module_function
  ##% egid() -> Integer
  def egid() 0 end
  ##% egid=(Integer) -> ?
  def egid=(gid) end
  ##% euid() -> Integer
  def euid() 0 end
  ##% euid=(Integer) -> ?
  def euid=(gid) end
  ##% getpgid(?Integer) -> Integer
  def getpgid(pid = 0) 0 end
  ##% getpgrp() -> Integer
  def getpgrp() 0 end
  ##% getpriority(Integer, Integer) -> Integer
  def getpriority(which, who) 0 end
  ##% getrlimit(Integer) -> (Integer, Integer)
  def getrlimit(resource) [0, 0] end
  ##% gid() -> Integer
  def gid() 0 end
  ##% gid=(Integer) -> ?
  def gid=(gid) end
  ##% groups() -> Array<Integer>
  def groups() [0] end
  ##% groups=(a) -> ?
  def groups=(gids) end
  ##% initgroups(String, Integer) -> Array<Integer>
  def initgroups(user, group) [0] end
  ##% kill(Integer or String, Integer, *Integer) -> Integer
  def kill(signal, pid, *rest) 0 end
  ##% maxgroups() -> Integer
  def maxgroups() 0 end
  ##% maxgroups=(Integer) -> ?
  def maxgroups=(num) end
  ##% pid() -> Integer
  def pid() 0 end
  ##% ppid() -> Integer
  def ppid() 0 end
  ##% setpgid(Integer, Integer) -> Integer
  def setpgid(pid, pgrp) 0 end
  ##% setpgrp() -> Integer
  def setpgrp() 0 end
  ##% setpriority(Integer, Integer, Integer) -> Integer
  def setpriority(which, who, prio) 0 end
  ##% setrlimit(Integer, Integer, ?Integer) -> nil
  def setrlimit(resource, cur_limit, max_limit = nil) nil end
  ##% setsid() -> Integer
  def setsid() 0 end
  ##% times() -> Struct::Tms
  def times() Struct::Tms.new end
  ##% uid() -> Integer
  def uid() 0 end
  ##% uid=(Integer) -> ?
  def uid=(id) end
  ##% wait() -> Integer
  def wait() 0 end
  ##% wait2() -> (Integer, Process::Status)
  def wait2() [0, Process::Status.new] end
  ##% waitall() -> Array<(Integer, Process::Status)>
  def waitall() [[0, Process::Status.new]] end
  ##% waitpid(Integer, ?Integer) -> Integer
  def waitpid(pid, flags = 0) 0 end
  ##% waitpid2(Integer, ?Integer) -> (Integer, Process::Status)
  def waitpid2(pid, flags = 0) [0, Process::Status.new] end
end

module Process::GID
  module_function
  ##% change_privilege(Integer) -> Integer
  def change_privilege(id) 0 end
  ##% eid() -> Integer
  def eid() 0 end
  ##% grant_privilege(Integer) -> Integer
  def grant_privilege(id) 0 end
  alias :eid= :grant_privilege
  ##% re_exchange() -> Integer
  def re_exchange() 0 end
  ##% re_exchangeable?() -> Boolean
  def re_exchangeable?() BOOLEAN end
  ##% rid() -> Integer
  def rid() 0 end
  ##% sid_available?() -> Boolean
  def sid_available?() BOOLEAN end
  ##% switch() -> Integer
  ##% switch() {() -> a} -> a
  def switch() yield; 0 end
end

class Process::Status
  ##% &(Integer) -> Integer
  def &(other) Integer end
  ##% ==(a) -> Boolean
  def ==(other) BOOLEAN end
  ##% ">>"(Integer) -> Integer
  def >>(num) 0 end
  ##% coredump?() -> Boolean
  def coredump?() BOOLEAN end
  ##% exited?() -> Boolean
  def exited?() BOOLEAN end
  ##% exitstatus() -> Integer
  def exitstatus() 0 end
  ##% inspect() -> String
  def inspect() '' end
  ##% pid() -> Integer
  def pid() 0 end
  ##% signaled?() -> Boolean
  def signaled?() BOOLEAN end
  ##% stopped?() -> Boolean
  def stopped?() BOOLEAN end
  ##% stopsig() -> Integer
  def stopsig() 0 end
  ##% success?() -> Boolean
  def success?() BOOLEAN end
  ##% termsig() -> Integer
  def termsig() 0 end
  ##% to_i() -> Integer
  def to_i() 0 end
  alias :to_int :to_i
  ##% to_s() -> String
  def to_s() '' end
end

module Process::Sys
  module_function
  ##% getegid() -> Integer
  def getegid() 0 end
  ##% geteuid() -> Integer
  def geteuid() 0 end
  ##% getgid() -> Integer
  def getgid() 0 end
  ##% getuid() -> Integer
  def getuid() 0 end
  ##% issetugid() -> Boolean
  def issetugid() BOOLEAN end
  ##% setegid(Integer) -> nil
  def setegid(id) nil end
  ##% seteuid(Integer) -> nil
  def seteuid(id) nil end
  ##% setgid(Integer) -> nil
  def setgid(id) nil end
  ##% setregid(Integer, Integer) -> nil
  def setregid(rid, eid) nil end
  ##% setresgid(Integer, Integer, Integer) -> nil
  def setresgid(rid, eid, sid) nil end
  ##% setresuid(Integer, Integer, Integer) -> nil
  def setresuid(rid, eid, sid) nil end
  ##% setreuid(Integer, Integer) -> nil
  def setreuid(rid, eid) nil end
  ##% setrgid(Integer) -> nil
  def setrgid(id) nil end
  ##% setruid(Integer) -> nil
  def setruid(id) nil end
  ##% setuid(Integer) -> nil
  def setuid(id) nil end
end

module Process::UID
  module_function
  ##% change_privilege(Integer) -> Integer
  def change_privilege(id) 0 end
  ##% eid() -> Integer
  def eid() 0 end
  ##% grant_privilege(Integer) -> Integer
  def grant_privilege(id) 0 end
  alias :eid= :grant_privilege
  ##% re_exchange() -> Integer
  def re_exchange() 0 end
  ##% re_exchangeable?() -> Boolean
  def re_exchangeable?() BOOLEAN end
  ##% rid() -> Integer
  def rid() 0 end
  ##% sid_available?() -> Boolean
  def sid_available?() BOOLEAN end
  ##% switch() -> Integer
  ##% switch() {() -> a} -> a
  def switch() yield; 0 end
end

##% Range<t>
class Range
  include Enumerable

  ##% self.new(a, b, ?Boolean) -> Range<a or b>
  def self.new() end

  ##% ==(a) -> Boolean
  def ==(other) BOOLEAN end
  ##% ===(a) -> Boolean
  def ===(other) BOOLEAN end
  alias :include? :===
  alias :member? :===
  ##% begin() -> t
  def begin() end
  alias :first :begin
  ##% each() {t -> ?} -> self
  ##% each() -> Enumerator<self, t>
  def each() self end
  ##% end() -> t
  def end() end
  alias :last :end
  ##% eql?(a) -> Boolean
  def eql?(other) BOOLEAN end
  ##% equal?(a) -> Boolean
  def equal?(other) BOOLEAN end
  ##% exclude_end?() -> Boolean
  def exclude_end?() BOOLEAN end
  ##% hash() -> Integer
  def hash() 0 end
  ##% step(?Fixnum) {t -> ?} -> self
  ##% step(?Fixnum) -> Enumerator<self, t>
  def step(s = 1) end
end

class Regexp
  EXTENDED = 0
  IGNORECASE = 0
  MULTILINE = 0
  
  ##% self.compile(String, Fixnum or Boolean, String) -> Regexp
  def self.compile(string, option = nil, code = nil) Regexp.new end
  ##% self.escape(String, ?String) -> String
  def self.escape(string, kcode = $KCODE) '' end
  ##% self.last_match() -> MatchData
  ##% self.last_match(Integer) -> MatchData
  def self.last_match(n = nil) MatchData.new end
  ##% self.union(*(String or Regexp)) -> Regexp
  def self.union(*pattern) Regexp.new end

  ##% ==(Regexp) -> Boolean
  def ==(other) BOOLEAN end
  alias :eql? :==
  ##% =~(String) -> Fixnum
  def =~(string) 0 end
  ##% ===(string) -> Boolean
  def ===(string) BOOLEAN end
  ##% casefold?() -> Boolean
  def casefold?() BOOLEAN end
  ##% hash() -> Fixnum
  def hash() 0 end
  ##% inspect() -> String
  def inspect() '' end
  ##% kcode() -> String
  def kcode() '' end
  ##% match(String) -> MatchData
  def match(str) MatchData.new end
  ##% options() -> Integer
  def options() 0 end
  ##% source() -> String
  def source() '' end
  ##% to_s() -> String
  def to_s() '' end
  ##% ~() -> Fixnum
  def ~() 0 end
end

module Signal
  module_function
  ##% list() -> Hash<String, Integer>
  def list() {'' => 1} end
  ##% trap(String or Symbol, String or Proc) -> String or Proc
  ##% trap(String or Symbol) {() -> ?} -> String or Proc
  def trap(signal, command = nil) yield; '' end
end

##% String<| String <= t>
class String
  include Enumerable
  include Comparable
  
  ##% self.new(?String) -> String
  def self.new(string = '') '' end

  ##% %(a) -> String
  def %(args) '' end
  ##% "*"(Integer) -> String
  def *(times) '' end
  ##% +(String) -> String
  def +(other) '' end
  ##% "<<"(?Fixnum or String) -> self
  def <<(other) self end
  alias :concat :<<
  ##% "<=>"(String) -> Integer
  def <=>(other) 0 end
  ##% ==(a) -> Boolean
  def ==(other) BOOLEAN end
  # FIXME =~
  ##% =~(a) -> Integer
  def =~(other) 0 end
  ##% [](Integer) -> Fixnum
  ##% [](Integer, Integer) -> String
  ##% [](String) -> String
  ##% [](Regexp, ?Integer) -> String
  ##% [](Range) -> String
  def [](*) 0 end
  alias :slice :[]
  ##% []=(Integer, String or Integer) -> String
  ##% []=(Integer, Integer, String or Integer) -> String
  ##% []=(String, String or Integer) -> String
  ##% []=(Regexp, ?Integer, String or Integer) -> String
  ##% []=(Range, String or Integer) -> String
  def []=(*) '' end
  ##% each_byte() {Fixnum -> ?} -> self
  ##% each_byte() -> Enumerator<self, Fixnum>
  def each_byte() yield 0; self end
  alias :bytes :each_byte
  ##% bytesize() -> Integer
  def bytesize() 0 end
  ##% capitalize() -> String
  def capitalize() '' end
  ##% capitalize!() -> self
  def capitalize!() '' end
  ##% casecmp(String) -> Integer
  def casecmp(other) 0 end
  ##% center(Integer, ?String) -> String
  def center(width, padding = ' ') '' end
  ##% each_char() {String -> ?} -> self
  ##% each_char() -> Enumerator<self, String>
  def each_char() yield ''; self end
  alias :chars :each_char
  ##% chomp(?String) -> String
  def chomp(rs = $/) '' end
  ##% chomp!(?String) -> self
  def chomp!(rs = $/) self end
  ##% chop() -> String
  def chop() '' end
  ##% chop!() -> self
  def chop!() self end
  ##% count(*String) -> Integer
  def count(*chars) 0 end
  ##% crypt(String) -> String
  def crypt(salt) '' end
  ##% delete(*String) -> String
  def delete(*strs) '' end
  ##% delete!(*String) -> self
  def delete!(*strs) self end
  ##% downcase() -> String
  def downcase() '' end
  ##% downcase!() -> self
  def downcase!() self end
  ##% dump() -> String
  def dump() '' end
  ##% each(?String) {String -> ?} -> self
  ##% each(?String) -> Enumerator<self, String>
  def each(rs = $/) yield ''; self end
  alias :each_line :each
  alias :lines :each
  ##% empty?() -> Boolean
  def empty?() Boolean end
  ##% end_with?(String) -> Boolean
  def end_with?(str) BOOLEAN end
  ##% eql?(a) -> Boolean
  def eql?(other) BOOLEAN end
  ##% gsub(String or Regexp, String) -> String
  ##% gsub(String or Regexp) {String -> ?} -> String
  ##% gsub(String or Regexp) -> Enumerator<String, String>
  def gsub(pattern, replace = nil) '' end
  ##% gsub!(String or Regexp, String) -> self
  ##% gsub!(String or Regexp) {String -> ?} -> self
  ##% gsub!(String or Regexp) -> Enumerator<String, self>
  def gsub!(pattern, replace = nil) self end
  ##% hash() -> Integer
  def hash() 0 end
  ##% hex() -> Integer
  def hex() 0 end
  ##% include?(String or Integer) -> Boolean
  def include?(substr) BOOLEAN end
  ##% index(String or Regexp, ?Integer) -> Integer
  def index(pattern, pos = 0) 0 end
  ##% insert(Integer, String) -> self
  def insert(pos, other) self end
  ##% inspect() -> String
  def inspect() '' end
  ##% intern() -> Symbol
  def intern() :a end
  alias :to_sym :intern
  ##% length() -> Integer
  def length() 0 end
  alias :length :size
  ##% ljust(Integer, ?String) -> String
  def ljust(width, padding = ' ') '' end
  ##% lstrip() -> String
  def lstrip() '' end
  ##% lstrip!() -> self
  def lstrip!() self end
  ##% match(String or Regexp) -> MatchData
  def match(regexp) MatchData.new end
  ##% succ() -> String
  def succ() '' end
  alias :next :succ
  ##% succ!() -> self
  def succ!() self end
  alias :next! :succ!
  ##% oct() -> Integer
  def oct() 0 end
  ##% partition(String or Regexp) -> (String, String, String)
  def partition(sep) ['', '', ''] end
  ##% replace(String) -> String
  def replace(other) self end
  ##% reverse() -> String
  def reverse() '' end
  ##% reverse!() -> self
  def reverse!() self end
  ##% rindex(String or Regexp, ?Integer) -> Integer
  def rindex(pattern, pos = 0) 0 end
  ##% rjust(Integer, ?String) -> String
  def rjust(width, padding = ' ') '' end
  ##% rpartition(String or Regexp) -> (String, String, String)
  def rpartition(sep) ['', '', ''] end
  ##% rstrip() -> String
  def rstrip() '' end
  ##% rstrip!() -> self
  def rstrip!() self end
  ##% scan(String or Regexp) -> Array<String> or Array<Array<String> >
  ##% scan(String or Regexp) {String -> ?} -> self
  def scan(re) [''] || [['']] end
  alias :slice! :[]
  ##% split(?(String or Regexp), ?Integer) -> Array<String> or Array<Array<String> >
  def split(sep = $;, limit = 0) [''] || [['']] end
  ##% squeeze(*String) -> String
  def squeeze(*chars) '' end
  ##% squeeze!(*String) -> self
  def squeeze!(*chars) self end
  ##% start_with?(String) -> Boolean
  def start_with?(str) BOOLEAN end
  ##% strip() -> String
  def strip() '' end
  ##% strip!() -> self
  def strip!() self end
  ##% sub(String or Regexp, String) -> String
  ##% sub(String or Regexp) {String -> ?} -> String
  ##% sub(String or Regexp) -> Enumerator<String, String>
  def sub(pattern, replace = nil) '' end
  ##% sub!(String or Regexp, String) -> self
  ##% sub!(String or Regexp) {String -> ?} -> self
  ##% sub!(String or Regexp) -> Enumerator<String, self>
  def sub!(pattern, replace = nil) self end
  ##% sum(?Integer) -> Integer
  def sum(bits = 16) 0 end
  ##% swapcase() -> String
  def swapcase() '' end
  ##% swapcase!() -> self
  def swapcase!() self end
  ##% to_f() -> Float
  def to_f() 0.0 end
  ##% to_i(?Integer) -> Integer
  def to_i(base = 10) 0 end
  ##% to_s() -> self
  def to_s() self end
  alias :to_str :to_s
  ##% tr(String, String) -> String
  def tr(pattern, replace) '' end
  ##% tr!(String, String) -> self
  def tr!(pattern, replace) self end
  ##% tr_s(String, String) -> String
  def tr_s(pattern, replace) '' end
  ##% tr_s!(String, String) -> self
  def tr_s!(pattern, replace) self end
  ##% unpack(String) -> Array
  def unpack(template) [] end
  ##% upcase() -> String
  def upcase() '' end
  ##% upcase!() -> self
  def upcase!() self end
  ##% upto(String, ?Boolean) {String -> ?} -> self
  def upto(max, exclusive = false) yield ''; self end
end

class Struct
  # FIXME
end

class Struct::Tms
  # FIXME
end

class Symbol
  ##% self.all_symbols() -> Array<Symbol>
  def all_symbols() [:a] end

  ##% id2name() -> String
  def id2name() '' end
  alias :to_s :id2name
  ##% inspect() -> String
  def inspect() '' end
  ##% to_i() -> Integer
  def to_i() 0 end
  alias :to_int :to_i
  ##% to_proc() -> Proc
  def to_proc() Proc.new end
  ##% to_sym() -> self
  def to_sym() self end
end

class Thread
  ##% self.abort_on_exception() -> Boolean
  def self.abort_on_exception() BOOLEAN end
  ##% self.abort_on_exception=(Boolean) -> ?
  def self.abort_on_exception=(newstate) end
  ##% self.critical() -> Boolean
  def self.critical() BOOLEAN end
  ##% self.critical=(Boolean) -> ?
  def self.critical=(newstate) end
  ##% self.current() -> Thread
  def self.current() Thread.new {} end
  ##% self.exit() -> ?
  def self.exit() end
  ##% self.start(*a) {*a -> ?} -> Thread
  def self.start(*arg) yield *arg; Thread.current end
  ##% self.fork(*a) {*a -> ?} -> Thread
  def self.fork(*arg) yield *arg; Thread.current end
  ##% self.kill(Thread) -> Thread
  def self.kill(thread) thread end
  ##% self.list() -> [Thead]
  def self.list() [Thread.current] end
  ##% self.main() -> Thread
  def self.main() Thread.current end
  ##% self.new(*a) {*a -> ?} -> Thread
  def self.new(*arg) yield *arg; super() end
  ##% self.pass() -> nil
  def self.pass() nil end
  ##% self.stop() -> nil
  def self.stop() nil end

  ##% [](Symbol or String) -> Object
  def [](name) Object.new end
  ##% []=(Symbol or String, a) -> a
  def []=(name, val) val end
  ##% abort_on_exception() -> Boolean
  def abort_on_exception() BOOLEAN end
  ##% abort_on_exception=(Boolean) -> ?
  def abort_on_exception=(newstate) end
  ##% alive?() -> Boolean
  def alive?() BOOLEAN end
  ##% exit() -> self
  def exit() self end
  alias :kill :exit
  alias :terminate :exit
  ##% exit!() -> self
  def exit!() self end
  alias :kill! :exit!
  alias :terminate! :exit!
  ##% group() -> ThreadGroup
  def group() ThreadGroup.new end
  ##% join() -> self
  ##% join(Numeric) -> self
  def join(limit = nil) self end
  ##% key?(Symbol or String) -> Boolean
  def key?(name) BOOLEAN end
  ##% keys() -> Array<Symbol>
  def keys() [:a] end
  ##% priority() -> Integer
  def priority() 0 end
  ##% priority=(Integer) -> nil
  def priority=(val) nil end
  ##% raise() -> nil
  ##% raise(String) -> nil
  ##% raise(?, ?String, Array<String>) -> nil
  def raise(*) end
  ##% run() -> self
  def run() self end
  ##% safe_level() -> Integer
  def safe_level() 0 end
  ##% status() -> String or FalseClass
  def status() '' || false end
  ##% stop?() -> Boolean
  def stop?() BOOLEAN end
  ##% value() -> Object
  def value() Object.new end
  ##% wakeup() -> self
  def wakeup() self end
end

class ThreadGroup
  Default = ThreadGroup.new

  ##% add(Thread) -> self
  def add(thread) self end
  ##% enclose() -> self
  def enclose() self end
  ##% enclosed?() -> Boolean
  def enclosed?() BOOLEAN end
  ##% list() -> Array<Thread>
  def list() [Thread.current] end
end

class Time
  include Comparable

  ##% self.at(Time or Integer, ?Integer) -> Time
  def self.at(time, usec) Time.new end
  ##% self.gm(Integer or String, Integer or String, Integer or String, Integer or String, Integer or String, Integer or String, Integer or String, Integer or String) -> Time
  ##% self.gm(Integer or String, Integer or String, Integer or String, Integer or String, Integer or String, Integer or String, Integer or String, a, b, Boolean, c) -> Time
  def self.gm(*) Time.new end
  ##% self.utc(Integer or String, Integer or String, Integer or String, Integer or String, Integer or String, Integer or String, Integer or String, Integer or String) -> Time
  ##% self.utc(Integer or String, Integer or String, Integer or String, Integer or String, Integer or String, Integer or String, Integer or String, a, b, Boolean, c) -> Time
  def self.utc(*) Time.new end
  ##% self.local(Integer or String, Integer or String, Integer or String, Integer or String, Integer or String, Integer or String, Integer or String, Integer or String) -> Time
  ##% self.local(Integer or String, Integer or String, Integer or String, Integer or String, Integer or String, Integer or String, Integer or String, a, b, Boolean, c) -> Time
  def self.local(*) Time.new end
  ##% self.mktime(Integer or String, Integer or String, Integer or String, Integer or String, Integer or String, Integer or String, Integer or String, Integer or String) -> Time
  ##% self.mktime(Integer or String, Integer or String, Integer or String, Integer or String, Integer or String, Integer or String, Integer or String, a, b, Boolean, c) -> Time
  def self.mktime(*) Time.new end
  ##% self.now() -> Time
  def self.now() Time.new end
  ##% self.times() -> Struct::Tms
  def self.times() Struct::Tms.new end
  
  ##% +(Integer) -> Time
  def +(other) Time.new end
  ##% -(Integer or Time) -> Time
  def -(other) Time.new end
  ##% "<=>"(Time) -> Fixnum
  def <=>(other) 0 end
  ##% asctime() -> String
  def asctime() '' end
  alias :ctime :asctime
  ##% mday() -> Integer
  def mday() 0 end
  alias :day :mday
  ##% isdst() -> Boolean
  def isdst() BOOLEAN end
  alias :dst? :isdst
  ##% eql?(Time) -> Boolean
  def eql?(other) BOOLEAN end
  ##% getgm() -> Time
  def getgm() Time.new end
  alias :getutc :getgm
  ##% getlocal() -> Time
  def getlocal() Time.new end
  ##% gmt?() -> Boolean
  def gmt?() BOOLEAN end
  alias :utc? :gmt?
  ##% utc_offset() -> Integer
  def utc_offset() 0 end
  alias :gmt_offset :utc_offset
  alias :gmtoff :utc_offset
  ##% gmtime() -> self
  def gmtime() self end
  ##% hour() -> Integer
  def hour() 0 end
  ##% localtime() -> self
  def localtime() self end
  ##% min() -> Integer
  def min() 0 end
  ##% mon() -> Integer
  def mon() 0 end
  alias :month :mon
  ##% sec() -> Integer
  def sec() 0 end
  ##% strftime(String) -> String
  def strftime(format) '' end
  ##% succ() -> Time
  def succ() Time.new end
  ##% to_a() -> (Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Boolean, String)
  def to_a() [0, 0, 0, 0, 0, 0, 0, 0, BOOLEAN, ''] end
  ##% to_f() -> Float
  def to_f() 0.0 end
  ##% to_i() -> Integer
  def to_i() 0 end
  alias :tv_sec :to_i
  ##% to_s() -> String
  def to_s() '' end
  ##% usec() -> Integer
  def usec() 0 end
  alias :tv_usec :usec
  ##% wday() -> Integer
  def wday() 0 end
  ##% yday() -> Integer
  def yda() 0 end
  ##% year() -> Integer
  def year() 0 end
  ##% zone() -> String
  def zone() '' end
end

class TrueClass
  ##% &(a) -> Boolean
  def %(other) BOOLEAN end
  ##% ^(a) -> Boolean
  def ^(other) BOOLEAN end
  ##% to_s() -> String
  def to_s() '' end
  ##% "|"(a) -> Boolean
  def |(other) BOOLEAN end
end

class UnboundMethod
  # FIXME
end

class Exception; end
class StandardError < Exception; end
class IOError < StandardError; end
class SystemCallError < StandardError; end
class RangeError < StandardError; end
class SignalException < Exception; end
class ScriptError < Exception; end

class ArgumentError < StandardError; end
class EOFError < IOError; end
class Errno::E2BIG < SystemCallError; end
class Errno::EACCES < SystemCallError; end
class Errno::EADDRINUSE < SystemCallError; end
class Errno::EADDRNOTAVAIL < SystemCallError; end
class Errno::EADV < SystemCallError; end
class Errno::EAFNOSUPPORT < SystemCallError; end
class Errno::EAGAIN < SystemCallError; end
class Errno::EALREADY < SystemCallError; end
class Errno::EBADE < SystemCallError; end
class Errno::EBADF < SystemCallError; end
class Errno::EBADFD < SystemCallError; end
class Errno::EBADMSG < SystemCallError; end
class Errno::EBADR < SystemCallError; end
class Errno::EBADRQC < SystemCallError; end
class Errno::EBADSLT < SystemCallError; end
class Errno::EBFONT < SystemCallError; end
class Errno::EBUSY < SystemCallError; end
class Errno::ECHILD < SystemCallError; end
class Errno::ECHRNG < SystemCallError; end
class Errno::ECOMM < SystemCallError; end
class Errno::ECONNABORTED < SystemCallError; end
class Errno::ECONNRESET < SystemCallError; end
class Errno::EDEADLK < SystemCallError; end
class Errno::EDEADLOCK < SystemCallError; end
class Errno::EDESTADDRREQ < SystemCallError; end
class Errno::EDOM < SystemCallError; end
class Errno::EDOTDOT < SystemCallError; end
class Errno::EDQUOT < SystemCallError; end
class Errno::EEXIST < SystemCallError; end
class Errno::EFAULT < SystemCallError; end
class Errno::EFBIG < SystemCallError; end
class Errno::EHOSTDOWN < SystemCallError; end
class Errno::EHOSTUNREACH < SystemCallError; end
class Errno::EIDRM < SystemCallError; end
class Errno::EILSEQ < SystemCallError; end
class Errno::EINPROGRESS < SystemCallError; end
class Errno::EINTR < SystemCallError; end
class Errno::EINVAL < SystemCallError; end
class Errno::EIO < SystemCallError; end
class Errno::EISCONN < SystemCallError; end
class Errno::EISDIR < SystemCallError; end
class Errno::EISNAM < SystemCallError; end
class Errno::EL2HLT < SystemCallError; end
class Errno::EL2NSYNC < SystemCallError; end
class Errno::EL3HLT < SystemCallError; end
class Errno::EL3RST < SystemCallError; end
class Errno::ELIBACC < SystemCallError; end
class Errno::ELIBBAD < SystemCallError; end
class Errno::ELIBEXEC < SystemCallError; end
class Errno::ELIBMAX < SystemCallError; end
class Errno::ELIBSCN < SystemCallError; end
class Errno::ELNRNG < SystemCallError; end
class Errno::ELOOP < SystemCallError; end
class Errno::EMFILE < SystemCallError; end
class Errno::EMLINK < SystemCallError; end
class Errno::EMSGSIZE < SystemCallError; end
class Errno::EMULTIHOP < SystemCallError; end
class Errno::ENAMETOOLONG < SystemCallError; end
class Errno::ENAVAIL < SystemCallError; end
class Errno::ENETDOWN < SystemCallError; end
class Errno::ENETRESET < SystemCallError; end
class Errno::ENETUNREACH < SystemCallError; end
class Errno::ENFILE < SystemCallError; end
class Errno::ENOANO < SystemCallError; end
class Errno::ENOBUFS < SystemCallError; end
class Errno::ENOCSI < SystemCallError; end
class Errno::ENODATA < SystemCallError; end
class Errno::ENODEV < SystemCallError; end
class Errno::ENOENT < SystemCallError; end
class Errno::ENOEXEC < SystemCallError; end
class Errno::ENOLCK < SystemCallError; end
class Errno::ENOLINK < SystemCallError; end
class Errno::ENOMEM < SystemCallError; end
class Errno::ENOMSG < SystemCallError; end
class Errno::ENONET < SystemCallError; end
class Errno::ENOPKG < SystemCallError; end
class Errno::ENOPROTOOPT < SystemCallError; end
class Errno::ENOSPC < SystemCallError; end
class Errno::ENOSR < SystemCallError; end
class Errno::ENOSTR < SystemCallError; end
class Errno::ENOSYS < SystemCallError; end
class Errno::ENOTBLK < SystemCallError; end
class Errno::ENOTCONN < SystemCallError; end
class Errno::ENOTDIR < SystemCallError; end
class Errno::ENOTEMPTY < SystemCallError; end
class Errno::ENOTNAM < SystemCallError; end
class Errno::ENOTSOCK < SystemCallError; end
class Errno::ENOTTY < SystemCallError; end
class Errno::ENOTUNIQ < SystemCallError; end
class Errno::ENXIO < SystemCallError; end
class Errno::EOPNOTSUPP < SystemCallError; end
class Errno::EOVERFLOW < SystemCallError; end
class Errno::EPERM < SystemCallError; end
class Errno::EPFNOSUPPORT < SystemCallError; end
class Errno::EPIPE < SystemCallError; end
class Errno::EPROTO < SystemCallError; end
class Errno::EPROTONOSUPPORT < SystemCallError; end
class Errno::EPROTOTYPE < SystemCallError; end
class Errno::ERANGE < SystemCallError; end
class Errno::EREMCHG < SystemCallError; end
class Errno::EREMOTE < SystemCallError; end
class Errno::EREMOTEIO < SystemCallError; end
class Errno::ERESTART < SystemCallError; end
class Errno::EROFS < SystemCallError; end
class Errno::ERROR < SystemCallError; end
class Errno::ESHUTDOWN < SystemCallError; end
class Errno::ESOCKTNOSUPPORT < SystemCallError; end
class Errno::ESPIPE < SystemCallError; end
class Errno::ESRCH < SystemCallError; end
class Errno::ESRMNT < SystemCallError; end
class Errno::ESTALE < SystemCallError; end
class Errno::ESTRPIPE < SystemCallError; end
class Errno::ETIME < SystemCallError; end
class Errno::ETIMEDOUT < SystemCallError; end
class Errno::ETOOMANYREFS < SystemCallError; end
class Errno::ETXTBSY < SystemCallError; end
class Errno::EUCLEAN < SystemCallError; end
class Errno::EUNATCH < SystemCallError; end
class Errno::EUSERS < SystemCallError; end
class Errno::EWOULDBLOCK < SystemCallError; end
class Errno::EXDEV < SystemCallError; end
class Errno::EXFULL < SystemCallError; end
class Errno::EXXX < SystemCallError; end

class Exception
  ##% self.new(?String) -> Exception
  def self.new(error_message = nil) super() end
  ##% self.exception(?String) -> Exception
  def self.exception(error_message = nil) self.new(error_message) end

  ##% backtrace() -> Array<String>
  def backtrace() [''] end
  ##% exception() -> self
  ##% exception(String) -> Exception
  def exception(error_message = nil) self end
  ##% message() -> String
  def message() '' end
  alias :to_s :message
  alias :to_str :message
  ##% set_backtrace(String or Array<String>) -> String or Array<String>
  def set_backtrace(errinfo) '' || [''] end
end

class FloatDomainError < RangeError; end
class IndexError < StandardError; end
class Interrupt < SignalException; end
class LoadError < ScriptError; end

class LocalJumpError < StandardError
  ##% exit_value() -> Object
  def exit_value() Object.new end
  ##% reason() -> Symbol
  def reason() :a end
end

class NameError < StandardError
  ##% self.new(String) -> NameError
  def self.new(error_message) super() end

  ##% name() -> Symbol
  def name() :a end
  ##% to_s() -> String
  def to_s() '' end
end

class NoMemoryError < Exception; end

class NoMethodError < NameError
  ##% self.new(?String) -> Exception
  def self.new(error_message = nil) super() end

  ##% args() -> Array<Object>
  def args() [Object.new] end
end

class NotImplementedError < ScriptError; end
class RangeError < StandardError; end
class RegexpError < StandardError; end
class RuntimeError < StandardError; end
class ScriptError < Exception; end
class SecurityError < StandardError; end
class SignalException < Exception; end
class StandardError < Exception; end
class StopIteration < IndexError; end
class SyntaxError < ScriptError; end

class SystemCallError < StandardError
  ##% self.new(String, ?Integer) -> SystemCallError
  ##% self.new(Integer) -> SystemCallError
  def self.new(*) super() end

  ##% errono() -> Fixnum
  def errorno() 0 end
end

class SystemExit < Exception
  ##% self.new(?Integer, ?String) -> SystemExit
  def self.new(status = 0, error_message = '') super() end

  ##% status() -> Fixnum
  def status() 0 end
  ##% success?() -> Boolean
  def success?() BOOLEAN end
end

class SystemStackError < StandardError; end
class ThreadError < StandardError; end
class TypeError < StandardError; end
class ZeroDivisionError < StandardError; end
