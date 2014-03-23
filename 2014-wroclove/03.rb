d=','

regexp1 = /(\A|#{d})\s*'(.*?)'\s*(?=#{d}\s*|\z)/

tag_string = %[tag1, 'tag with spaces', 'tag3']

tag_string =~ regexp1 # => 4


regexp2 = /(\A|#{d})         # Match the start of the string, or the delimiter
           \s*               # any number of spaces
           '(.*?)'           # anything between single quotes, lazily,
                             # this is what we really want to capture
           \s*               # any number of spaces
           (?=#{d}\s*|\z)/x  # zero-width lookahead, match delimter+spaces, or end of string


tag_string =~ regexp2 # => 4
