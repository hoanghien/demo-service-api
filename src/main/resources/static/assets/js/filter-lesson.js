$('.filter-list-dropdown li').click(function() {
	var act = $(this).find('ul').css( "display" );
	if(act == 'block')
	{
		$(this).find('#arrow-show').removeClass('fas fa-angle-up');
		$(this).find('#arrow-show').addClass('fas fa-angle-down');
		$(this).removeClass('active');
		$(this).find('ul').hide(500);
	}
	else
	{
		$(this).find('#arrow-show').removeClass('fas fa-angle-down');
		$(this).find('#arrow-show').addClass('fas fa-angle-up');
		$(this).addClass('active');
		$(this).find('ul').slideDown("slow");
	}
});

$('.js-filter-mobile-button').click(function() {
	var act = $('.filter-item').css('display');
	if(act == 'block')
	{
		$('.filter-item').css('display','none');
	}
	else
	{
		$('.filter-item').css('display','block');
	}
});