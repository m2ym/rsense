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

let s:rsenseCompletionKindDictionary = {'CLASS': 'C', 'MODULE': 'M', 'CONSTANT': 'c', 'METHOD': 'm'}

function! s:rsenseProgram()
    return g:rsenseHome . '/bin/rsense'
endfunction

function! s:rsenseCommand(args)
    for i in range(0, len(a:args) - 1)
        let a:args[i] = shellescape(a:args[i])
    endfor
    return s:system(printf('ruby %s %s %s',
                           \ shellescape(s:rsenseProgram()),
                           \ join(a:args, ' '),
                           \ shellescape('--detect-project=' . bufname('%'))))
endfunction

function! s:rsenseCurrentBufferFile()
    let buf = getline(1, '$')
    let file = tempname()
    call writefile(buf, file)
    return file
endfunction

function! s:rsenseCurrentBufferFileOption()
    return '--file=' . s:rsenseCurrentBufferFile()
endfunction

function! s:rsenseCurrentLocationOption()
    return printf('--location=%s:%s', line('.'), col('.') - (mode() == 'n' ? 0 : 1))
endfunction

function! RSenseCompleteFunction(findstart, base)
    if a:findstart
        let cur_text = strpart(getline('.'), 0, col('.') - 1)
        return match(cur_text, '[^\.:]*$')
    else
        let result = split(s:rsenseCommand(['code-completion',
                                            \ s:rsenseCurrentBufferFileOption(),
                                            \ s:rsenseCurrentLocationOption(),
                                            \ '--prefix=' . a:base]),
                           \ "\n")
        let completions = []
        for item in result
            if item =~ '^completion: '
                let ary = split(item, ' ')
                let dict = { 'word': ary[1] }
                if len(ary) > 4
                    let dict['menu'] = ary[3]
                    let dict['kind'] = s:rsenseCompletionKindDictionary[ary[4]]
                endif
                call add(completions, dict)
            endif
        endfor
        return completions
    endif
endfunction

function! RSenseTypeHelp()
    let result = split(s:rsenseCommand(['type-inference', s:rsenseCurrentBufferFileOption(), s:rsenseCurrentLocationOption()]), "\n")
    let types = []
    for item in result
        if item =~ '^type: '
            call add(types, split(item, ' ')[1])
        endif
    endfor
    return len(types) == 0 ? 'No type information' : join(types, ' | ')
endfunction

function! RSenseJumpToDefinition()
    let tempfile = s:rsenseCurrentBufferFile()
    let result = split(s:rsenseCommand(['find-definition',
                                       \ '--file=' . tempfile,
                                       \ s:rsenseCurrentLocationOption()]),
                       \ "\n")
    for item in result
        " TODO selection interface
        if item =~ '^location: '
            let ary = split(item, ' ')
            let file = join(ary[2:], ' ')
            let line = ary[1]
            " Unmap for tempfile
            if file == tempfile
                let file = bufname('%')
            endif
            execute printf("edit +%s %s", line, file)
            return
        endif
    endfor
    echo 'No definition found'
endfunction

function! RSenseWhereIs()
    let result = split(s:rsenseCommand(['where', s:rsenseCurrentBufferFileOption(), '--line=' . line('.')]), "\n")
    for item in result
        if item =~ '^name: '
            echo split(item, ' ')[1]
            return
        endif
    endfor
    echo 'Unknown'
endfunction

function! RSenseVersion()
    return s:rsenseCommand(['version'])
endfunction

function! RSenseServiceStart()
    return s:rsenseCommand(['service', 'start'])
endfunction

function! RSenseServiceStop()
    return s:rsenseCommand(['service', 'stop'])
endfunction

function! RSenseServiceStatus()
    return s:rsenseCommand(['service', 'status'])
endfunction

function! RSenseOpenProject(directory)
    call s:rsenseCommand(['open-project', expand(a:directory)])
endfunction

function! RSenseCloseProject(project)
    call s:rsenseCommand(['close-project', expand(a:project)])
endfunction

function! RSenseClear()
    call s:rsenseCommand(['clear'])
endfunction

function! RSenseExit()
    call s:rsenseCommand(['exit'])
endfunction

command! -narg=0 RSenseTypeHelp         echo RSenseTypeHelp()
command! -narg=0 RSenseJumpToDefinition call RSenseJumpToDefinition()
command! -narg=0 RSenseWhereIs          call RSenseWhereIs()
command! -narg=0 RSenseVersion          echo RSenseVersion()
command! -narg=0 RSenseServiceStart     echo RSenseServiceStart()
command! -narg=0 RSenseServiceStop      echo RSenseServiceStop()
command! -narg=0 RSenseServiceStatus    echo RSenseServiceStatus()
command! -narg=0 RSenseClear            call RSenseClear()
command! -narg=0 RSenseExit             call RSenseExit()
command! -narg=1 RSenseOpenProject      call RSenseOpenProject('<args>')
command! -narg=1 RSenseCloseProject     call RSenseCloseProject('<args>')

function! SetupRSense()
    if g:rsenseUseOmniFunc
        setlocal omnifunc=RSenseCompleteFunction
    else
        setlocal completefunc=RSenseCompleteFunction
    endif
endfunction

autocmd FileType ruby call SetupRSense()
