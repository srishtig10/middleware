/**
 * 
 */

$(function() {
  var moveLeft = 20;
  var moveDown = 10;

  $('a#trigger').hover(function(e) {
    $('div#pop-up').show()
      .css('top', e.pageY + moveDown)
      .css('left', e.pageX + moveLeft)
      .appendTo('body');
  }, function() {
    $('div#pop-up').hide();
  });
});