shower.modules.define('shower-highlight', [
    'util.extend'
], function (provide, extend) {

    var shower = null;

    function onSlideChange(event) {
        console.log("change", arguments);
        console.log('[name=' + (event._data.index+1) + '] .highlight');
        $('.highlight-enter').removeClass('highlight-enter');
        $('#' + (event._data.index+1) + ' .highlight').addClass('highlight-enter');
    }

    function Highlight(_shower, options) {
        shower = _shower;
        shower.player.events.group().on('activate', onSlideChange, this);
    }

    provide(Highlight);
});


shower.modules.require(['shower'], function (sh) {
    sh.plugins.add('shower-highlight');
});
