#!/usr/bin/env ruby

ARGV.each do |file|
  open(file) do |f|
    htmlfile = File.dirname(file) + '/' + File.basename(file, '.txt') + '.html'
    system "maruku --html #{file} -o #{htmlfile}"
    html = IO.read(htmlfile)
    html.gsub!(/href='(\w+?)\.txt'/, "href='\\1.html'")
    html.sub!(/<\/body>/, <<EOF)
    <hr />
  <div style="text-align: right">
    Author: Tomohiro Matsuyama &lt;<a href="mailto: m2ym.pub@gmail.com">m2ym.pub@gmail.com</a>&gt;<br />
    URL: <a href="http://cx4a.org/">cx4a.org</a><br />
  </div>
</body>
EOF
    open(htmlfile, 'w') {|f| f.write(html)}
  end
end
