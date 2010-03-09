#!/usr/bin/env ruby

require 'rubygems'

puts "home = #{File.expand_path(File.dirname(File.dirname($0)))}"
puts "load-path = #{$:.join(File::PATH_SEPARATOR)}"
puts "gem-path = #{Gem.path.join(File::PATH_SEPARATOR)}"
