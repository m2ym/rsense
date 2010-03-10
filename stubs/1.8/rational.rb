class Rational < Numeric
  ##% self.new!(Fixnum, Fixnum) -> Rational
  def self.new!(num, den = 1) Rational.new end
  ##% self.reduce(Fixnum, Fixnum) -> Rational
  def self.reduce(num, den = 1) Rational.new end

  ##% %(Float) -> Float
  ##% %(Numeric) -> Rational
  def %(other) 0.0 || Rational.new end
  ##% "*"(Float) -> Float
  ##% "*"(Numeric) -> Rational
  def *(other) 0.0 || Rational.new end
  ##% **(Integer) -> Rational
  ##% **(Numeric) -> Float
  def **(other) 0.0 || Rational.new end
  ##% +(Float) -> Float
  ##% +(Numeric) -> Rational
  def +(other) 0.0 || Rational.new end
  ##% -(Float) -> Float
  ##% -(Numeric) -> Rational
  def -(other) 0.0 || Rational.new end
  ##% /(Float) -> Float
  ##% /(Numeric) -> Rational
  def /(other) 0.0 || Rational.new end
  ##% "<=>"(a) -> Integer
  def <=>(other) 0 end
  ##% abs() -> Rational
  def abs() self end
  ##% denominator() -> Fixnum
  def denominator() 0 end
  ##% divmod(Float) -> (Fixnum, Float)
  ##% divmod(Numeric) -> (Fixnum, Rational)
  def divmod(other) [0, 0.0] || [0, Rational.new] end
  ##% numerator() -> Fixnum
  def numerator() 0 end
  ##% to_f() -> Float
  def to_f() 0.0 end
  ##% to_i() -> Integer
  def to_i() 0 end
  ##% to_s() -> String
  def to_s() '' end
end

module Kernel
  ##% Rational(Fixnum, Fixnum) -> Rational
  def Rational(num, den = 1) Rational.new end
end
