function selectTab(digest, tab_select) {
    let tabs = ['result', 'tree', 'zipper'];
    for (var i = 0; i < tabs.length; i++) {
        tab_type = tabs[i];
        let div = document.querySelector('#div_' + digest + '_' + tab_type);
        let tab = document.querySelector('#tab_' + digest + '_' + tab_type);
        if (tab_type == tab_select) {
            div.classList.remove('dn');
            tab.classList.add('selected');
        } else {
            div.classList.add('dn');
            tab.classList.remove('selected');
        }
    }
}
