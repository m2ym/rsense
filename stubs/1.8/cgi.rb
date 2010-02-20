require_next

class CGI
  # emulate CGI#initialize
  include QueryExtension
  include Html4Tr
  include HtmlExtension

  module QueryExtension
    ##% content_length() -> Integer
    def content_length() 0 end
    ##% server_port() -> Integer
    def server_port() 0 end
    ##% auth_type() -> String
    def auth_type() '' end
    ##% content_type() -> String
    def content_type() '' end
    ##% gateway_interface() -> String
    def gateway_interface() '' end
    ##% path_info() -> String
    def path_info() '' end
    ##% path_translated() -> String
    def path_translated() '' end
    ##% query_string() -> String
    def query_string() '' end
    ##% remote_addr() -> String
    def remote_addr() '' end
    ##% remote_host() -> String
    def remote_host() '' end
    ##% remote_ident() -> String
    def remote_ident() '' end
    ##% remote_user() -> String
    def remote_user() '' end
    ##% request_method() -> String
    def request_method() '' end
    ##% script_name() -> String
    def script_name() '' end
    ##% server_name() -> String
    def server_name() '' end
    ##% server_protocol() -> String
    def server_protocol() '' end
    ##% server_software() -> String
    def server_software() '' end
    ##% accept() -> String
    def accept() '' end
    ##% accept_charset() -> String
    def accept_charset() '' end
    ##% accept_encoding() -> String
    def accept_encoding() '' end
    ##% accept_language() -> String
    def accept_language() '' end
    ##% cache_control() -> String
    def cache_control() '' end
    ##% from() -> String
    def from() '' end
    ##% host() -> String
    def host() '' end
    ##% negotiate() -> String
    def negotiate() '' end
    ##% pragma() -> String
    def pragma() '' end
    ##% referer() -> String
    def referer() '' end
    ##% user_agent() -> String
    def user_agent() '' end
  end

  # FIXME Html3, Html4, Html4Fr
  module Html4Tr
    ##% tt(a) -> String
    ##% tt(a) {() -> ?} -> String
    def tt(attributes = nil) yield; '' end
    ##% i(a) -> String
    ##% i(a) {() -> ?} -> String
    def i(attributes = nil) yield; '' end
    ##% b(a) -> String
    ##% b(a) {() -> ?} -> String
    def b(attributes = nil) yield; '' end
    ##% u(a) -> String
    ##% u(a) {() -> ?} -> String
    def u(attributes = nil) yield; '' end
    ##% s(a) -> String
    ##% s(a) {() -> ?} -> String
    def s(attributes = nil) yield; '' end
    ##% strike(a) -> String
    ##% strike(a) {() -> ?} -> String
    def strike(attributes = nil) yield; '' end
    ##% big(a) -> String
    ##% big(a) {() -> ?} -> String
    def big(attributes = nil) yield; '' end
    ##% small(a) -> String
    ##% small(a) {() -> ?} -> String
    def small(attributes = nil) yield; '' end
    ##% em(a) -> String
    ##% em(a) {() -> ?} -> String
    def em(attributes = nil) yield; '' end
    ##% strong(a) -> String
    ##% strong(a) {() -> ?} -> String
    def strong(attributes = nil) yield; '' end
    ##% dfn(a) -> String
    ##% dfn(a) {() -> ?} -> String
    def dfn(attributes = nil) yield; '' end
    ##% code(a) -> String
    ##% code(a) {() -> ?} -> String
    def code(attributes = nil) yield; '' end
    ##% samp(a) -> String
    ##% samp(a) {() -> ?} -> String
    def samp(attributes = nil) yield; '' end
    ##% kbd(a) -> String
    ##% kbd(a) {() -> ?} -> String
    def kbd(attributes = nil) yield; '' end
    ##% var(a) -> String
    ##% var(a) {() -> ?} -> String
    def var(attributes = nil) yield; '' end
    ##% cite(a) -> String
    ##% cite(a) {() -> ?} -> String
    def cite(attributes = nil) yield; '' end
    ##% abbr(a) -> String
    ##% abbr(a) {() -> ?} -> String
    def abbr(attributes = nil) yield; '' end
    ##% acronym(a) -> String
    ##% acronym(a) {() -> ?} -> String
    def acronym(attributes = nil) yield; '' end
    ##% font(a) -> String
    ##% font(a) {() -> ?} -> String
    def font(attributes = nil) yield; '' end
    ##% sub(a) -> String
    ##% sub(a) {() -> ?} -> String
    def sub(attributes = nil) yield; '' end
    ##% sup(a) -> String
    ##% sup(a) {() -> ?} -> String
    def sup(attributes = nil) yield; '' end
    ##% span(a) -> String
    ##% span(a) {() -> ?} -> String
    def span(attributes = nil) yield; '' end
    ##% bdo(a) -> String
    ##% bdo(a) {() -> ?} -> String
    def bdo(attributes = nil) yield; '' end
    ##% address(a) -> String
    ##% address(a) {() -> ?} -> String
    def address(attributes = nil) yield; '' end
    ##% div(a) -> String
    ##% div(a) {() -> ?} -> String
    def div(attributes = nil) yield; '' end
    ##% center(a) -> String
    ##% center(a) {() -> ?} -> String
    def center(attributes = nil) yield; '' end
    ##% map(a) -> String
    ##% map(a) {() -> ?} -> String
    def map(attributes = nil) yield; '' end
    ##% object(a) -> String
    ##% object(a) {() -> ?} -> String
    def object(attributes = nil) yield; '' end
    ##% applet(a) -> String
    ##% applet(a) {() -> ?} -> String
    def applet(attributes = nil) yield; '' end
    ##% h1(a) -> String
    ##% h1(a) {() -> ?} -> String
    def h1(attributes = nil) yield; '' end
    ##% h2(a) -> String
    ##% h2(a) {() -> ?} -> String
    def h2(attributes = nil) yield; '' end
    ##% h3(a) -> String
    ##% h3(a) {() -> ?} -> String
    def h3(attributes = nil) yield; '' end
    ##% h4(a) -> String
    ##% h4(a) {() -> ?} -> String
    def h4(attributes = nil) yield; '' end
    ##% h5(a) -> String
    ##% h5(a) {() -> ?} -> String
    def h5(attributes = nil) yield; '' end
    ##% h6(a) -> String
    ##% h6(a) {() -> ?} -> String
    def h6(attributes = nil) yield; '' end
    ##% pre(a) -> String
    ##% pre(a) {() -> ?} -> String
    def pre(attributes = nil) yield; '' end
    ##% q(a) -> String
    ##% q(a) {() -> ?} -> String
    def q(attributes = nil) yield; '' end
    ##% ins(a) -> String
    ##% ins(a) {() -> ?} -> String
    def ins(attributes = nil) yield; '' end
    ##% del(a) -> String
    ##% del(a) {() -> ?} -> String
    def del(attributes = nil) yield; '' end
    ##% dl(a) -> String
    ##% dl(a) {() -> ?} -> String
    def dl(attributes = nil) yield; '' end
    ##% ol(a) -> String
    ##% ol(a) {() -> ?} -> String
    def ol(attributes = nil) yield; '' end
    ##% ul(a) -> String
    ##% ul(a) {() -> ?} -> String
    def ul(attributes = nil) yield; '' end
    ##% dir(a) -> String
    ##% dir(a) {() -> ?} -> String
    def dir(attributes = nil) yield; '' end
    ##% menu(a) -> String
    ##% menu(a) {() -> ?} -> String
    def menu(attributes = nil) yield; '' end
    ##% label(a) -> String
    ##% label(a) {() -> ?} -> String
    def label(attributes = nil) yield; '' end
    ##% select(a) -> String
    ##% select(a) {() -> ?} -> String
    def select(attributes = nil) yield; '' end
    ##% optgroup(a) -> String
    ##% optgroup(a) {() -> ?} -> String
    def optgroup(attributes = nil) yield; '' end
    ##% fieldset(a) -> String
    ##% fieldset(a) {() -> ?} -> String
    def fieldset(attributes = nil) yield; '' end
    ##% legend(a) -> String
    ##% legend(a) {() -> ?} -> String
    def legend(attributes = nil) yield; '' end
    ##% button(a) -> String
    ##% button(a) {() -> ?} -> String
    def button(attributes = nil) yield; '' end
    ##% table(a) -> String
    ##% table(a) {() -> ?} -> String
    def table(attributes = nil) yield; '' end
    ##% iframe(a) -> String
    ##% iframe(a) {() -> ?} -> String
    def iframe(attributes = nil) yield; '' end
    ##% noframes(a) -> String
    ##% noframes(a) {() -> ?} -> String
    def noframes(attributes = nil) yield; '' end
    ##% title(a) -> String
    ##% title(a) {() -> ?} -> String
    def title(attributes = nil) yield; '' end
    ##% style(a) -> String
    ##% style(a) {() -> ?} -> String
    def style(attributes = nil) yield; '' end
    ##% script(a) -> String
    ##% script(a) {() -> ?} -> String
    def script(attributes = nil) yield; '' end
    ##% noscript(a) -> String
    ##% noscript(a) {() -> ?} -> String
    def noscript(attributes = nil) yield; '' end
    ##% textarea(a) -> String
    ##% textarea(a) {() -> ?} -> String
    def textarea(attributes = nil) yield; '' end
    ##% form(a) -> String
    ##% form(a) {() -> ?} -> String
    def form(attributes = nil) yield; '' end
    ##% a(a) -> String
    ##% a(a) {() -> ?} -> String
    def a(attributes = nil) yield; '' end
    ##% blockquote(a) -> String
    ##% blockquote(a) {() -> ?} -> String
    def blockquote(attributes = nil) yield; '' end
    ##% caption(a) -> String
    ##% caption(a) {() -> ?} -> String
    def caption(attributes = nil) yield; '' end
  end
end
