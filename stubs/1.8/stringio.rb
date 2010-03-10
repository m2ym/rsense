class StringIO < Data
  ##% self.new(?String, ?a) -> StringIO
  def self.new(string = '', mode = 'r+') super() end
  ##% self.open(?String, ?a) -> StringIO
  def self.open(string = '', mode = 'r+') super() end
  ##% self.open(?String, ?a) {StringIO -> ?} -> StringIO
  def self.open(string = '', mode = 'r+')
    io = StringIO.new
    yield io
    io
  end
  
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
  def fcntl() nil end
  ##% fileno() -> nil
  def fileno() nil end
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
  ##% isatty() -> FalseClass
  def isatty() FALSE end
  alias :tty? :isatty
  ##% size() -> Integer
  def size() 0 end
  ##% length() -> Integer
  def length() 0 end
  ##% lineno() -> Integer
  def lineno() 0 end
  ##% lineno=(Integer) -> nil
  def lineno=(number) end
  ##% lines(?String) -> Enumerator<self, String>
  def lines(rs = $/) Enumerator.new end
  ##% path() -> nil
  def path() 0 end
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
  ##% string() -> String
  def string() '' end
  ##% string=(String) -> ?
  def string=(buf) nil end
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
  ##% truncate(Integer) -> Integer
  def truncate(len) 0 end
  ##% ungetc(a) -> nil
  def ungetc(char) end
  # FIXME to_s
  ##% write(a) -> Integer
  def write(str) 0 end
  # FIXME to_s
end
