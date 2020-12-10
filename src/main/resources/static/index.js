// (function($) {
//     'use strict';
//     $(function() {
//     var todoListItem = $('.todo-list');
//     var todoListInput = $('.todo-list-input');
//     $('.todo-list-add-btn').on("click", function(event) {
//     event.preventDefault();
    
//     var item = $(this).prevAll('.todo-list-input').val();
    
//     if (item) {
//         todoListItem.append("<li> <div class='form-check'><label class='form-check-label'><input class='checkbox' type='checkbox' />" + item + "<i class='input-helper'></i></label></div><i class='remove mdi mdi-close-circle-outline'></i></li>");
//         todoListInput.val("");
//     }
    
//     });
    
//     todoListItem.on('change', '.checkbox', function() {
//     if ($(this).attr('checked')) {
//     $(this).removeAttr('checked');
//     } else {
//     $(this).attr('checked', 'checked');
//     }
    
//     $(this).closest("li").toggleClass('completed');
    
//     });
    
//     todoListItem.on('click', '.remove', function() {
//     $(this).parent().remove();
//     });
    
//     });
// })(jQuery);

var todoListItem = document.querySelector('.todo-list');
var todoListInput = document.querySelector('.todo-list-input');

function prevAll(element) {
    var result = [];

    while (element = element.previousElementSibling)
        result.push(element);
    return result;
}

document
.querySelector('.todo-list-add-btn')
.addEventListener("click", function(event) {
    event.preventDefault();

    var item = prevAll('.to-do-list');  // post

    if (item) {
        let listItem = document.createElement("li");
        listItem.innerHTML = "<div class='form-check'><label class='form-check-label'><input class='checkbox' type='checkbox' />"
        + item + "<i class='input-helper'></i></label></div><i class='remove mdi mdi-close-circle-outline'></i><";
        todoListItem.append(listItem);
        todoListInput.value = ("");
    }
});

todoListItem.addEventListener('change', '.checkbox', function () {
    if (this.attr('checked')) {
        this.removeAttribute('checked');
    } else {
        this.attribute('checked', 'checked');
    }
    this.closest("li").toggleClass('completed');
});

todoListItem.addEventListener('click', '.remove', function() {
    this.parent().remove();
});


