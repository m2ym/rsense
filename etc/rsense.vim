let g:rsense_home = "~/src/rsense"

fun! RSenseComplete(findstart, base)
    if a:findstart
        let cur_text = strpart(getline('.'), 0, col('.') - 1)
        return match(cur_text, '[^\.:]*$')
    else
        let buf = getline(1, '$')
        let file = tempname()
        call writefile(buf, file)

        let command = printf('%s/bin/rsense code-completion --file=%s --location=%s:%s', g:rsense_home, file, line('.'), col('.') - 1)
        let result = split(system(command), "\n")
        let completions = []
        for item in result
            call add(completions, split(item, ' ')[1])
        endfor
        return completions
    endif
endfun
autocmd FileType ruby setlocal omnifunc=RSenseComplete
