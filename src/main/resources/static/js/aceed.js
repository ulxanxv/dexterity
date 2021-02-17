let editor = ace.edit("editor");

let editorLib = {
    init() {
        editor.setTheme("ace/theme/twilight");
        editor.getSession().setMode("ace/mode/java");

        editor.setOptions({
            enableBasicAutocompletion: true,
            enableLiveAutocompletion: true
        });
    }
}

editorLib.init();