if exists('g:loaded_rsense')
    finish
endif
let g:loaded_rsense = 1

if !exists('g:rsenseHome')
    let g:rsenseHome = expand("~/src/rsense")
endif

if !exists('g:rsenseUseOmniFunc')
    let g:rsenseUseOmniFunc = 0
endif

" Check vimproc.
let s:is_vimproc = exists('*vimproc#system')

function! s:system(str, ...)"{{{
  return s:is_vimproc ? (a:0 == 0 ? vimproc#system(a:str) : vimproc#system(a:str, join(a:000)))
        \: (a:0 == 0 ? system(a:str) : system(a:str, join(a:000)))
endfunction"}}}

function! RSenseComplete(findstart, base)
    if a:findstart
        let cur_text = strpart(getline('.'), 0, col('.') - 1)
        return match(cur_text, '[^\.:]*$')
    else
        let buf = getline(1, '$')
        let file = tempname()
        call writefile(buf, file)

        let rsense = shellescape(g:rsenseHome . '/bin/rsense')
        let file_opt = shellescape('--file=' . file)
        let prefix_opt = shellescape('--prefix=' . a:base)
        let command = printf('ruby %s code-completion %s --location=%s:%s %s',
                             \ rsense,
                             \ file_opt,
                             \ line('.'),
                             \ col('.') - 1,
                             \ prefix_opt)
        let result = split(s:system(command), "\n")
        let completions = []
        for item in result
            if item =~ '^completion: '
                call add(completions, split(item, ' ')[1])
            endif
        endfor
        return completions
    endif
endfunction

function! RSenseVersion()
    let rsense = shellescape(g:rsenseHome . '/bin/rsense')
    return s:system(printf('ruby %s version', rsense))
endfunction

function! SetupRSense()
    if g:rsenseUseOmniFunc
        setlocal omnifunc=RSenseComplete
    else
        setlocal completefunc=RSenseComplete
    endif
endfunction

autocmd FileType ruby call SetupRSense()
