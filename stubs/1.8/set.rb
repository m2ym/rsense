##% Set<t>
class Set
  include Enumerable

  ##% self.[]<v, u | v <= Array<u> >(*v) -> Set<u>
  def self.[](*ary) Set.new end
  ##% self.new<u>(?Enumerable<u>) -> Set<u>
  ##% self.new<u, u'>(?Enumerable<u>) {u -> u'} -> Set<u'>
  def self.new(enum = nil) super() end

  # FIXME not union
  ##% intersection<u>(Enumerable<u>) -> Set<t or u>
  def intersection(enum) Set.new end
  alias :& :intersection
  ##% union<u>(Enumerable<u>) -> Set<t or u>
  def union(enum) Set.new end
  alias :+ :union
  alias :| :union
  # FIXME
  ##% difference<u>(Enumerable<u>) -> Set<t>
  def difference(enum) Set.new end
  alias :- :difference
  ##% add<v | v <= t>(v) -> self
  def add(o) self end
  alias :<< :add
  alias :add? :add
  ##% ==(a) -> Boolean
  def ==(set) BOOLEAN end
  # FIXME
  ##% ^<u>(Enumerable<u>) -> Set<t or u>
  def ^(enum) Set.new end
  ##% classify() {t -> a} -> Hash<a, Set<t> >
  def classify() {(yield _elt) => self} end
  ##% clear() -> self
  def clear() self end
  ##% clone() -> Set<t>
  def clone() self end
  alias :dup :clone
  ##% collect!<v | v <= t>() {t -> v} -> self
  def collect!() yield _elt; self end
  alias :map! :collect!
  ##% delete(a) -> self
  def delete(o) self end
  alias :delete? :delete
  ##% delete_if() {t -> ?} -> self
  def delete_if() yield _elt; self end
  alias :reject! :delete_if
  ##% divide() {t -> ?} -> Set<Set<t> >
  ##% divide() {(t, t) -> ?} -> Set<Set<t> >
  def divide() yield _elt; Set[self] end
  ##% each() {t -> ?} -> self
  def each() yield _elt; self end
  ##% empty?() -> Boolean
  def empty?() BOOLEAN end
  ##% flatten() -> Set<t>
  def flatten() self end
  ##% flatten!() -> self
  def flatten!() self end
  ##% include?(a) -> Boolean
  def include?(o) BOOLEAN end
  alias :member? :include?
  ##% inspect() -> String
  def inspect() '' end
  ##% size() -> Integer
  def size() 0 end
  alias :length :size
  ##% merge<u | u <= t>(Enumerable<u>) -> self
  def merge(enum) self end
  ##% subset?(Set) -> Boolean
  def subset?(set) Boolean end
  alias :proper_subset? :subset?
  ##% superset?(Set) -> Boolean
  def superset?(set) BOOLEAN end
  alias :proper_superset? :superset?
  ##% replace<u | u <= t>(Enumerable<u>) -> self
  def replace(enum) self end
  ##% subtract(Enumerable) -> self
  def subtract(enum) self end
  ##% to_a() -> Array<t>
  def to_a() [_elt] end
  
  ##% _elt() -> t
  def _elt() end
  private :_elt
end

##% SortedSet<t>
class SortedSet < Set
  ##% self.[]<v, u | v <= Array<u> >(*v) -> SortedSet<u>
  def self.[](*ary) SortedSet.new end
  ##% self.new<u>(?Enumerable<u>) -> SortedSet<u>
  ##% self.new<u, u'>(?Enumerable<u>) {u -> u'} -> SortedSet<u'>
  def self.new(enum = nil) super() end
end
