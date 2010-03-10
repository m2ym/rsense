class Date
  ENGLAND = 0
  GREGORIAN = 0
  ITALY = 0
  JULIAN = 0

  include Comparable

  ##% self._parse(String, ?Boolean) -> Hash<Sybmol, Integer>
  def self._parse(str, complete = false) [:a => ''] end
  ##% self._strptime(String, ?String) -> Hash<Sybmol, Integer>
  def self._strptime(str, format = '%F') [:a => ''] end
  ##% self.civil(?Integer, ?Integer, ?Integer, ?Integer) -> Date
  def self.civil(year = -4712, mon = 1, mday = 1, start = Date::ITALY) Date.new(year, mon, mday, start) end
  ##% self.new(?Integer, ?Integer, ?Integer, ?Integer) -> Date
  def self.new(year = -4712, mon = 1, mday = 1, start = Date::ITALY) super() end
  ##% self.commercial(?Integer, ?Integer, ?Integer, ?Integer) -> Date
  def self.commercial(cwyear = 1582, cweek = 41, cwday = 5, start = Date::ITALY) Date.new(cwyear, cweek, cwday, start) end
  ##% self.gregorian_leap?(Integer) -> Boolean
  def self.gregorian_leap?(year) BOOLEAN end
  ##% self.leap?(Integer) -> Boolean
  def self.leap?(year) BOOLEAN end
  ##% self.jd(?Integer, ?Integer) -> Date
  def self.jd(js = 0, start = Date::ITALY) Date.new(0, 0, 0, start) end
  ##% self.julian_leap?(Integer) -> Boolean
  def self.julian_leap?(year) BOOLEAN end
  ##% self.ordinal(?Integer, ?Integer, ?Integer) -> Date
  def self.ordinal(year = -4712, yday = 1, start = Date::ITALY) Date.new(year, 0, 0, start) end
  ##% self.parse(?String, ?Boolean, ?Integer) -> Date
  def self.parse(str = '-4712-01-01', complete = false, start = Date::ITALY) Date.new(0, 0, 0, start) end
  ##% self.parse(?String, ?String, ?Integer) -> Date
  def self.parse(str = '-4712-01-01', format = '%F', start = Date::ITALY) Date.new(0, 0, 0, start) end
  ##% self.today(?Integer) -> Date
  def self.today(start = Date::ITALY) Date.new(0, 0, 0, start) end
  ##% self.valid_civil?(Integer, Integer, Integer, ?Integer) -> Numeric
  def self.valid_civil?(year, mon, mday, start = Date::GREGORIAN) 0 end
  ##% self.valid_date?(Integer, Integer, Integer, ?Integer) -> Numeric
  def self.valid_date?(year, mon, mday, start = Date::GREGORIAN) 0 end
  ##% self.valid_commercial?(Integer, Integer, Integer, ?Integer) -> Numeric
  def self.valid_commercial?(cwyear, cweek, cwday, start = Date::GREGORIAN) 0 end
  ##% self.valid_jd?(Integer, ?Integer) -> Numeric
  def self.valid_jd?(jd, start = Date::GREGORIAN) 0 end
  ##% self.valid_ordinal?(Integer, Integer, ?Integer) -> Numeric
  def self.valid_ordinal?(year, yday, start = Date::GREGORIAN) 0 end
  
  ##% +(Integer) -> Date
  def +(n) self end
  ##% -(Date) -> Rational
  ##% -(Integer) -> Date
  def -(n) self end
  ##% "<<"(Integer) -> Date
  def <<(n) self end
  ##% "<=>"(a) -> Integer
  def <=>(other) 0 end
  ##% ===(a) -> Boolean
  def ===(other) BOOLEAN end
  ##% ">>"(Integer) -> Date
  def >>(n) self end
  ##% ajd() -> Rational
  def ajd() Rational.new end
  ##% amjd() -> Rational
  def amjd() Rational.new end
  ##% asctime() -> String
  def asctime() '' end
  alias :ctime :asctime
  ##% cwday() -> Integer
  def cwday() 0 end
  ##% cweek() -> Integer
  def cweek() 0 end
  ##% cwyear() -> Integer
  def cwyear() 0 end
  ##% mday() -> Integer
  def mday() 0 end
  alias :day :mday
  ##% downto(Date) {Date -> ?} -> self
  def downto(min) yield self; self end
  ##% england() -> Date
  def england() self end
  ##% gregorian() -> Date
  def gregorian() self end
  ##% gregorian?() -> Boolean
  def gregorian?() BOOLEAN end
  ##% italy() -> Date
  def italy() self end
  ##% jd() -> Integer
  def jd() 0 end
  ##% julian() -> Date
  def julian() self end
  ##% julian?() -> Boolean
  def julian?() BOOLEAN end
  ##% ld() -> Integer
  def ld() 0 end
  ##% leap?() -> Boolean
  def leap?() BOOLEAN end
  ##% mon() -> Integer
  def mon() 0 end
  alias :month :mon
  ##% new_start(?Integer) -> Date
  def new_start(start = Date::ITALY) self end
  ##% succ() -> Date
  def succ() self end
  alias :next :succ
  ##% start() -> Integer
  def start() 0 end
  ##% step(Date, ?Integer) {Date -> ?} -> self
  def step(limit, step = 1) yield self; self end
  ##% strftime(?String) -> String
  def strftime(format = '%F') '' end
  ##% to_s() -> String
  def to_s() '' end
  ##% upto(Date) {Date -> ?} -> self
  def upto(min) yield self; self end
  ##% wday() -> Integer
  def wday() 0 end
  ##% yday() -> Integer
  def yday() 0 end
  ##% year() -> Integer
  def year() 0 end
end

class DateTime < Date
  ##% self.civil(?Integer, ?Integer, ?Integer, ?Integer, ?Integer, ?Integer, ?Integer, ?Integer) -> DateTime
  def self.civil(year = -4712, mon = 1, mday = 1, hour = 0, min = 0, sec = 0, offset = 0, start = Date::ITALY) DateTime.new(year, mon, mday, hour, min, sec, offset, start) end
  ##% self.new(?Integer, ?Integer, ?Integer, ?Integer, ?Integer, ?Integer, ?Integer, ?Integer) -> DateTime
  def self.new(year = -4712, mon = 1, mday = 1, hour = 0, min = 0, sec = 0, offset = 0, start = Date::ITALY) super() end
  ##% self.commercial(?Integer, ?Integer, ?Integer, ?Integer, ?Integer, ?Integer, ?Integer, ?Integer) -> DateTime
  def self.commercial(cwyear = 1582, cweek = 41, cwday = 5, hour = 0, min = 0, sec = 0, offset = 0, start = Date::ITALY) DateTime.new(0, 0, 0, 0, 0, 0, 0, start) end
  ##% self.jd(?Integer, ?Integer, ?Integer, ?Integer, ?Integer, ?Integer) -> DateTime
  def self.jd(jd = 0, hour = 0, min = 0, sec = 0, offset = 0, start = Date::ITALY) DateTime.new(0, 0, 0, 0, 0, 0, 0, start) end
  ##% self.now(?Integer) -> DateTime
  def self.now(start = Date::ITALY) DateTime.new(0, 0, 0, 0, 0, 0, 0, start) end
  ##% self.ordinal(?Integer, ?Integer, ?Integer, ?Integer, ?Integer, ?Integer, ?Integer) -> DateTime
  def self.ordinal(year = -4712, yday = 1, hour = 0, min = 0, sec = 0, offset = 0, start = Date::ITALY) DateTime.new(0, 0, 0, 0, 0, 0, 0, start) end

  ##% hour() -> Integer
  def hour() 0 end
  ##% min() -> Integer
  def min() 0 end
  ##% new_offset(?Integer) -> DateTime
  def new_offset(offset = 0) self end
  ##% offset() -> Integer
  def offset() 0 end
  ##% sec() -> Integer
  def sec() 0 end
  ##% zone() -> String
  def zone() '' end
end
