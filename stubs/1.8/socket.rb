class BasicSocket < IO
  ##% self.do_not_reverse_lookup() -> Boolean
  def self.do_not_reverse_lookup() BOOLEAN end
  ##% self.do_not_reverse_lookup=(Boolean) -> Boolean
  def self.do_not_reverse_lookup(bool) bool end
  ##% self.for_fd(Integer) -> BasicSocket
  def self.for_fd(fd) BasicSocket.new(0) end

  ##% getpeername() -> String
  def getpeername() '' end
  ##% getsockname() -> String
  def getsockname() '' end
  ##% getsockopt(Integer, Integer) -> String
  def getsockopt(level, optname) '' end
  ##% recv(Integer, ?Integer) -> String
  def recv(maxlen, flags = 0) '' end
  ##% recv_nonblock(Integer, ?Integer) -> String
  def recv_nonblock(maxlen, flags = 0) '' end
  ##% send(String, Integer, ?String) -> Fixnum
  def send(mesg, flags, dest_sockaddr = nil) 0 end
  ##% setsockopt(Integer, Integer, String) -> Fixnum
  def getsockopt(level, optname, optval) 0 end
  ##% shutdown(?Integer) -> Fixnum
  def shutdown(how = Socket::SHUT_RDWR) 0 end
end

class IPSocket < BasicSocket
  ##% self.getaddress(String) -> String
  def self.getaddress(host) '' end

  ##% addr() -> (String, Integer, String, String)
  def addr() ['', 0, '', ''] end
  ##% peeraddr()  -> (String, Integer, String, String)
  def peeraddr() ['', 0, '', ''] end
  ##% recvfrom(Integer, ?Integer) -> (String, (String, Integer, String, String))
  def recvfrom(maxlen, flags = 0) ['', ['', 0, '', '']] end
end

class TCPSocket < IPSocket
  ##% self.gethostbyname(String) -> (String, Array<String>, Integer, String)
  def self.gethostbyname(host) ['', [''], 0, ''] end
  ##% self.open(String, Integer or String, ?String, ?(Integer or String)) -> TCPSocket
  ##% self.open(String, Integer or String, ?String, ?(Integer or String)) {TCPSocket -> a} -> a
  def self.open(host, service, local_host = nil, local_service = nil)
    s = TCPSocket.new(host, service, local_host, local_service)
    (yield s) || s
  end
  ##% self.new(String, Integer or String, ?String, ?(Integer or String)) -> TCPSocket
  ##% self.new(String, Integer or String, ?String, ?(Integer or String)) {TCPSocket -> a} -> a
  def self.new(host, service, local_host = nil, local_service = nil)
    s = super()
    (yield s) || s
  end
end

class TCPServer < TCPSocket
  ##% self.new(Integer or String) -> TCPServer
  ##% self.new(Integer or String) {TCPServer -> a} -> a
  ##% self.new(String, Integer or String) -> TCPServer
  ##% self.new(String, Integer or String) {TCPServer -> a} -> a
  def self.new(*)
    s = super()
    (yield s) || s
  end
  ##% self.open(Integer or String) -> TCPServer
  ##% self.open(Integer or String) {TCPServer -> a} -> a
  ##% self.open(String, Integer or String) -> TCPServer
  ##% self.open(String, Integer or String) {TCPServer -> a} -> a
  def self.open(*)
    s = TCPServer.new(0)
    (yield s) || s
  end

  ##% accept() -> TCPSocket
  def accept() TCPSocket.new('', 0) end
  ##% accept_nonblock() -> TCPSocket
  def accept_nonblock() TCPSocket.new('', 0) end
  ##% listen(Integer) -> Fixnum
  def listen(backlog) 0 end
  ##% sysaccept() -> Fixnum
  def sysaccept() 0 end
end

class SOCKSSocket < TCPSocket
  ##% self.open(String, Integer or String) -> SOCKSSocket
  ##% self.open(String, Integer or String) {SOCKSSocket -> a} -> a
  def self.open(host, service)
    s = SOCKSSocket.new(host, service)
    (yield s) || s
  end
  ##% self.new(String, Integer or String) -> SOCKSSocket
  ##% self.new(String, Integer or String) {SOCKSSocket -> a} -> a
  def self.new(host, service)
    s = super()
    (yield s) || s
  end

  ##% close() -> nil
  def close() end
end

class UDPSocket < IPSocket
  ##% self.open(?Integer) -> UDPSocket
  ##% self.open(?Integer) {UDPSocket -> a} -> a
  def self.open(socktype = 0)
    s = UDPSocket.new
    (yield s) || s
  end
  ##% self.new(?Integer) -> UDPSocket
  ##% self.new(?Integer) {UDPSocket -> a} -> a
  def self.new(socktype = 0)
    s = super()
    (yield s) || s
  end

  ##% bind(String, Integer or String) -> Fixnum
  def bind(host, port) 0 end
  ##% connect(String, Integer or String) -> Fixnum
  def connect(host, port) 0 end
  ##% recvfrom_nonblock(Integer, ?Integer) -> (String, (String, Integer, String, String))
  def recvfrom_nonblock(maxlen, flags = 0) ['', ['', 0, '', '']] end
  ##% send(String, Integer, ?String) -> Fixnum
  ##% send(String, Integer, Integer or String, Integer or String) -> Fixnum
  def send(mesg, flags, *) 0 end
end

class UNIXSocket < BasicSocket
  ##% self.open(String) -> UNIXSocket
  ##% self.open(String) {UNIXSocket -> a} -> a
  def self.open(path)
    s = UNIXSocket.new(path)
    (yield s) || s
  end
  ##% self.new(String) -> UNIXSocket
  ##% self.new(String) {UNIXSocket -> a} -> a
  def self.new(path)
    s = super()
    (yield s) || s
  end
  ##% self.pair(?Integer, ?Integer) -> (UNIXSocket, UNIXSocket)
  def self.pair(type = 0, protocol = 0) [UNIXSocket.new(''), UNIXSocket.new('')] end
  ##% self.socketpair(?Integer, ?Integer) -> (UNIXSocket, UNIXSocket)
  def self.socketpair(type = 0, protocol = 0) [UNIXSocket.new(''), UNIXSocket.new('')] end
  
  ##% addr() -> (String, String)
  def addr() ['', ''] end
  ##% path() -> String
  def path() '' end
  ##% peeraddr() -> (String, String)
  def peeraddr() ['', ''] end
  ##% recv_io(?Class, ?Integer) -> Fixnum | IO
  def recv_io(klass = nil, mode = 0) 0 || IO.new(0) end
  ##% recvfrom(Integer, ?Integer) -> (String, String)
  def recvfrom(maxlen, flags = 0) ['', ''] end
  ##% send_io(IO) -> Fixnum
  def send_io(io) 0 end
end

class UNIXServer < UNIXSocket
  ##% self.open(String) -> UNIXServer
  ##% self.open(String) {UNIXServer -> a} -> a
  def self.open(path)
    s = UNIXServer.new(path)
    (yield s) || s
  end
  ##% self.new(String) -> UNIXServer
  ##% self.new(String) {UNIXServer -> a} -> a
  def self.new(path)
    s = super()
    (yield s) || s
  end

  ##% accept() -> UNIXSocket
  def accept() UNIXSocket.new('') end
  ##% accept_nonblock() -> UNIXSocket
  def accept_nonblock() UNIXSocket.new('') end
  ##% listen(Integer) -> Fixnum
  def listen(backlog) 0 end
  ##% sysaccept() -> Fixnum
  def sysaccept() 0 end
end

class Socket < BasicSocket
  module Constants
    AF_APPLETALK = 0
    AF_ATM = 0
    AF_AX25 = 0
    AF_CCITT = 0
    AF_CHAOS = 0
    AF_CNT = 0
    AF_COIP = 0
    AF_DATAKIT = 0
    AF_DEC = 0
    AF_DLI = 0
    AF_E164 = 0
    AF_ECMA = 0
    AF_HYLINK = 0
    AF_IMPLINK = 0
    AF_INET = 0
    AF_INET6 = 0
    AF_IPX = 0
    AF_ISDN = 0
    AF_ISO = 0
    AF_LAT = 0
    AF_LINK = 0
    AF_LOCAL = 0
    AF_MAX = 0
    AF_NATM = 0
    AF_NDRV = 0
    AF_NETBIOS = 0
    AF_NETGRAPH = 0
    AF_NS = 0
    AF_OSI = 0
    AF_PPP = 0
    AF_PUP = 0
    AF_ROUTE = 0
    AF_SIP = 0
    AF_SNA = 0
    AF_SYSTEM = 0
    AF_UNIX = 0
    AF_UNSPEC = 0

    AI_ADDRCONFIG = 0
    AI_ALL = 0
    AI_CANONNAME = 0
    AI_DEFAULT = 0
    AI_MASK = 0
    AI_NUMERICHOST = 0
    AI_PASSIVE = 0
    AI_V4MAPPED = 0
    AI_V4MAPPED_CFG = 0

    EAI_ADDRFAMILY = 0
    EAI_AGAIN = 0
    EAI_BADFLAGS = 0
    EAI_BADHINTS = 0
    EAI_FAIL = 0
    EAI_FAMILY = 0
    EAI_MAX = 0
    EAI_MEMORY = 0
    EAI_NODATA = 0
    EAI_NONAME = 0
    EAI_PROTOCOL = 0
    EAI_SERVICE = 0
    EAI_SOCKTYPE = 0
    EAI_SYSTEM = 0

    INADDR_ALLHOSTS_GROUP = 0
    INADDR_ANY = 0
    INADDR_BROADCAST = 0
    INADDR_LOOPBACK = 0
    INADDR_MAX_LOCAL_GROUP = 0
    INADDR_NONE = 0
    INADDR_UNSPEC_GROUP = 0

    IPPORT_RESERVED = 0
    IPPORT_USERRESERVED = 0

    IPPROTO_BIP = 0
    IPPROTO_EGP = 0
    IPPROTO_EON = 0
    IPPROTO_GGP = 0
    IPPROTO_HELLO = 0
    IPPROTO_ICMP = 0
    IPPROTO_IDP = 0
    IPPROTO_IGMP = 0
    IPPROTO_IP = 0
    IPPROTO_MAX = 0
    IPPROTO_ND = 0
    IPPROTO_PUP = 0
    IPPROTO_RAW = 0
    IPPROTO_TCP = 0
    IPPROTO_TP = 0
    IPPROTO_UDP = 0
    IPPROTO_XTP = 0

    MSG_COMPAT = 0
    MSG_CTRUNC = 0
    MSG_DONTROUTE = 0
    MSG_DONTWAIT = 0
    MSG_EOF = 0
    MSG_EOR = 0
    MSG_FLUSH = 0
    MSG_HAVEMORE = 0
    MSG_HOLD = 0
    MSG_OOB = 0
    MSG_PEEK = 0
    MSG_RCVMORE = 0
    MSG_SEND = 0
    MSG_TRUNC = 0
    MSG_WAITALL = 0

    NI_DGRAM = 0
    NI_MAXHOST = 0
    NI_MAXSERV = 0
    NI_NAMEREQD = 0
    NI_NOFQDN = 0
    NI_NUMERICHOST = 0
    NI_NUMERICSERV = 0

    PF_APPLETALK = 0
    PF_ATM = 0
    PF_AX25 = 0
    PF_CCITT = 0
    PF_CHAOS = 0
    PF_CNT = 0
    PF_COIP = 0
    PF_DATAKIT = 0
    PF_DEC = 0
    PF_DLI = 0
    PF_ECMA = 0
    PF_HYLINK = 0
    PF_IMPLINK = 0
    PF_INET = 0
    PF_INET6 = 0
    PF_IPX = 0
    PF_ISDN = 0
    PF_ISO = 0
    PF_KEY = 0
    PF_LAT = 0
    PF_LINK = 0
    PF_LOCAL = 0
    PF_MAX = 0
    PF_NATM = 0
    PF_NDRV = 0
    PF_NETBIOS = 0
    PF_NETGRAPH = 0
    PF_NS = 0
    PF_OSI = 0
    PF_PIP = 0
    PF_PPP = 0
    PF_PUP = 0
    PF_ROUTE = 0
    PF_RTIP = 0
    PF_SIP = 0
    PF_SNA = 0
    PF_SYSTEM = 0
    PF_UNIX = 0
    PF_UNSPEC = 0
    PF_XTP = 0

    SHUT_RD = 0
    SHUT_RDWR = 0
    SHUT_WR = 0

    SOCK_DGRAM = 0
    SOCK_PACKET = 0
    SOCK_RAW = 0
    SOCK_RDM = 0
    SOCK_SEQPACKET = 0
    SOCK_STREAM = 0

    SOL_ATALK = 0
    SOL_AX25 = 0
    SOL_IP = 0
    SOL_IPX = 0
    SOL_SOCKET = 0
    SOL_TCP = 0
    SOL_UDP = 0

    SOPRI_BACKGROUND = 0
    SOPRI_INTERACTIVE = 0
    SOPRI_NORMAL = 0

    SO_ACCEPTCONN = 0
    SO_ACCEPTFILTER = 0
    SO_ATTACH_FILTER = 0
    SO_BINDTODEVICE = 0
    SO_BROADCAST = 0
    SO_DEBUG = 0
    SO_DETACH_FILTER = 0
    SO_DONTROUTE = 0
    SO_DONTTRUNC = 0
    SO_ERROR = 0
    SO_KEEPALIVE = 0
    SO_LINGER = 0
    SO_NKE = 0
    SO_NOSIGPIPE = 0
    SO_NO_CHECK = 0
    SO_NREAD = 0
    SO_OOBINLINE = 0
    SO_PASSCRED = 0
    SO_PEERCRED = 0
    SO_PEERNAME = 0
    SO_PRIORITY = 0
    SO_RCVBUF = 0
    SO_RCVLOWAT = 0
    SO_RCVTIMEO = 0
    SO_REUSEADDR = 0
    SO_REUSEPORT = 0
    SO_SECURITY_AUTHENTICATION = 0
    SO_SECURITY_ENCRYPTION_NETWORK = 0
    SO_SECURITY_ENCRYPTION_TRANSPORT = 0
    SO_SNDBUF = 0
    SO_SNDLOWAT = 0
    SO_SNDTIMEO = 0
    SO_TIMESTAMP = 0
    SO_TYPE = 0
    SO_USELOOPBACK = 0
    SO_WANTMORE = 0
    SO_WANTOOBFLAG = 0

    TCP_MAXSEG = 0
    TCP_NODELAY = 0
  end

  include Constants

  ##% self.getaddrinfo(String or Integer, String or Integer, ?String, ?Integer, ?Integer, ?Integer) -> (String, Integer, String, String, Integer, Integer, Integer)
  def self.getaddrinfo(nodename, servname, family = nil, socktype = nil, protocol = nil, flags = nil) ['', 0, '', '', 0, 0, 0] end
  ##% self.gethostbyaddr(String, ?Integer) -> (String, Array<String>, Integer, String)
  def self.gethostbyaddr(host, type = nil) ['', [''], 0, ''] end
  ##% self.gethostbyname(String) -> (String, Array<String>, Integer, String)
  def self.gethostbyname(name) ['', [''], 0, ''] end
  ##% self.gethostname() -> String
  def self.gethostname() '' end
  ##% self.getnameinfo(String or Array, ?Integer) -> Array<String>
  def self.getnameinfo(sa, flags = nil) [''] end
  ##% self.getservbyname(String, ?Integer) -> Integer
  def self.getservbyname(service, proto = 0) 0 end
  ##% self.open(Integer or String, Integer or String, Integer) -> Socket
  def self.open(domain, type, protocol) Socket.new(domain, type, protocol) end
  ##% self.new(Integer or String, Integer or String, Integer) -> Socket
  def self.new(domain, type, protocol) super() end
  ##% self.sockaddr_in(Fixnum or String, String) -> String
  def self.sockaddr_in(port, host) '' end
  ##% self.pack_sockaddr_in(Fixnum or String, String) -> String
  def self.pack_sockaddr_in(port, host) '' end
  ##% self.sockaddr_un(String) -> String
  def self.sockaddr_un(path) '' end
  ##% self.pack_sockaddr_un(String) -> String
  def self.pack_sockaddr_un(path) '' end
  ##% self.pair(Integer or String, Integer or String, Integer) -> Socket
  def self.pair(domain, type, protocol) Socket.new(domain, type, protocol) end
  ##% self.socketpair(Integer or String, Integer or String, Integer) -> Socket
  def self.socketpair(domain, type, protocol) Socket.new(domain, type, protocol) end
  ##% self.unpack_sockaddr_in(String) -> (Fixnum, String)
  def self.unpack_sockaddr_in(sockaddr) [0, ''] end
  ##% self.unpack_sockaddr_un(String) -> String
  def self.unpack_sockaddr_un(sockaddr) String end

  # FIXME
  ##% accept() -> (Socket, String)
  def accept() [Socket.new(0, 0, 0), ''] end
  ##% accept_nonblock() -> (Socket, String)
  def accept_nonblock() [Socket.new(0, 0, 0), ''] end
  ##% bind(String) -> Fixnum
  def bind(my_sockaddr) 0 end
  ##% connect(String) -> Fixnum
  def connect(server_sockaddr) 0 end
  ##% connect_nonblock(String) -> Fixnum
  def connect_nonblock(server_sockaddr) 0 end
  ##% listen(Integer) -> Fixnum
  def listen(backlog) 0 end
  ##% recvfrom(Integer, ?Integer) -> (String, String)
  def recvfrom(maxlen, flags = 0) ['', ''] end
  ##% recvfrom_nonblock(Integer, ?Integer) -> (String, String)
  def recvfrom_nonblock(maxlen, flags = 0) ['', ''] end
  ##% sysaccept() -> (Fixnum, String)
  def sysaccept() [0, ''] end
end
