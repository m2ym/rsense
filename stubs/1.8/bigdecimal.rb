class BigDecimal < Numeric
  BASE = 0
  EXCEPTION_ALL = 0
  EXCEPTION_INFINITY = 0
  EXCEPTION_NaN = 0
  EXCEPTION_OVERFLOW = 0
  EXCEPTION_UNDERFLOW = 0
  EXCEPTION_ZERODIVIDE = 0
  ROUND_CEILING = 0
  ROUND_DOWN = 0
  ROUND_FLOOR = 0
  ROUND_HALF_DOWN = 0
  ROUND_HALF_EVEN = 0
  ROUND_HALF_UP = 0
  ROUND_MODE = 0
  ROUND_UP = 0
  SIGN_NEGATIVE_FINITE = 0
  SIGN_NEGATIVE_INFINITE = 0
  SIGN_NEGATIVE_ZERO = 0
  SIGN_NaN = 0
  SIGN_POSITIVE_FINITE = 0
  SIGN_POSITIVE_INFINITE = 0
  SIGN_POSITIVE_ZERO = 0
  
  ##% self.double_fig() -> Integer
  def self.double_fig() 0 end
  ##% self.induced_from(a) -> BigDecimal
  def self.induced_from(number) BigDecimal.new('') end
  ##% self.limit(?Integer) -> Integer
  def self.limit(n = 0) 0 end
  ##% self.mode(Integer, ?Boolean) -> Boolean
  def self.mode(s, v = nil) BOOLEAN end
  ##% self.new(String, ?Integer) -> BigDecimal
  def self.new(s, n = 0) super() end
  ##% self.ver() -> String
  def self.ver() '' end
  
  ### Numeric
  ##% +@() -> self
  def +@() self end
  ##% -@() -> BigDecimal
  def -@() BigDecimal.new('') end
  ##% "<=>"(other) -> Fixnum
  def <=>(other) 0 end
  ##% abs() -> BigDecimal
  def abs() self end
  ##% clone() -> self
  def clone() self end
  alias :dup :clone
  ##% coerce(Float) -> (Float, Float)
  ##% coerce(Numeric) -> (Fixnum, Fixnum)
  def coerce(other) [0.0, 0.0] end
  ##% divmod(Numeric) -> (BigDecimal, Numeric)
  def divmod(other) [BigDecimal.new(''), 0] end
  ##% eql?(Numeric) -> Boolean
  def eql?(other) BOOLEAN end
  ##% quo(Numeric) -> BigDecimal
  def quo(other) 0.0 end
  ##% fdiv(Numeric) -> BigDecimal
  def fdiv(other) 0.0 end
  ##% integer?() -> Boolean
  def integer?() BOOLEAN end
  ##% modulo(Numeric) -> BigDecimal
  def modulo(other) 0 end
  ##% nonzero?() -> self
  def nonzero?() self end
  ##% remainder(Numeric) -> BigDecimal
  def remainder(other) 0 end
  ##% step<a | a <= Numeric>(Numeric, ?a) {self or a or Fixnum -> ?} -> self
  ##% step<a | a <= Numeric>(Numeric, ?a) -> Enumerator<self, self or a or Fixnum>
  def step(limit, step = 1) self end
  ##% zero?() -> Boolean
  def zero?() BOOLEAN end

  ##% _dump() -> String
  def _dump() '' end
  ##% add(Numeric, Integer) -> BigDecimal
  def add(b, n) BigDecimal.new('') end
  ##% ceil(?Integer) -> BigDecimal
  def ceil(n = 0) 0 end
  ##% div(Numeric, ?Integer) -> BigDecimal
  def div(other, n = 0) BigDecimal.new('') end
  ##% exponent() -> Fixnum
  def exponent() 0 end
  ##% finite?() -> Boolean
  def finite?() BOOLEAN end
  ##% fix() -> Fixnum
  def fix() 0 end
  ##% floor(?Integer) -> BigDecimal
  def floor(n = 0) BigDecimal.new('') end
  ##% flac() -> BigDecimal
  def flac() BigDecimal.new('') end
  ##% infinite?() -> Boolean
  def infinite?() BOOLEAN end
  ##% mult(Numeric, Integer) -> BigDecimal
  def mult(b, n) BigDecimal.new('') end
  ##% nan?() -> Boolean
  def nan?() BOOLEAN end
  ##% nonzero?() -> Boolean
  def nonzero?() BOOLEAN end
  ##% power(Integer) -> BigDecimal
  def power(n) BigDecimal.new('') end
  ##% precs() -> (Fixnum, Fixnum)
  def precs() [0, 0] end
  ##% round(Integer, ?Integer) -> Fixnum | Float
  def round(n, b = 0) 0 || 0.0 end
  ##% sign() -> Fixnum
  def sign() 0 end
  ##% split() -> (Fixnum, String, Fixnum, Fixnum)
  def split() [0, '', 0, 0] end
  ##% sqrt(Fixnum) -> BigDecimal
  def sqrt(n) BigDecimal.new('') end
  ##% sub(Numeric, Integer) -> BigDecimal
  def sub(b, n) BigDecimal.new('') end
  ##% to_f() -> Float
  def to_f() 0.0 end
  ##% to_i() -> Fixnum | Bignum
  def to_i() 0 end
  ##% to_int() -> Integer
  def to_int() 0 end
  ##% to_s(?Fixnum) -> String
  def to_s(n = 0) '' end
  ##% truncate() -> BigDecimal
  def truncate() BigDecimal.new('') end
end

module Kernel
  ##% BigDecimal(String, ?Fixnum) -> BigDecimal
  def BigDecimal(s, n = 0) BigDecimal.new('') end
  module_function :BigDecimal
end
