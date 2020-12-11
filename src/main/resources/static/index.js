var todoListItem = document.querySelector('.todo-list');
var todoListInput = document.querySelector('.todo-list-input');

document
.querySelector('.todo-list-add-btn')
.addEventListener("click", function(event) {
    event.preventDefault();

    var newItem = todoListInput.value;  // post

    if (newItem) {
        let newListItem = document.createElement("li");
        newListItem.innerHTML = "<div class='form-check'><label class='form-check-label'><input class='checkbox' type='checkbox' />"
        + newItem + "<i class='input-helper'></i></label></div> <i class='fas fa-edit'></i> <i class='fas fa-trash-alt'></i>";
        todoListItem.append(newListItem);
        todoListInput.value = ("");
        setCheckboxListener(newListItem.querySelector('.checkbox'));
        setRemoveListener(newListItem.querySelector('.fa-trash-alt'));
    }
});

function setCheckboxListener(item) {

    item.addEventListener('change', function () {
        if (this.getAttribute('checked')) {
            this.removeAttribute('checked');
        } else {
            this.setAttribute('checked', 'checked');
        }
        this.closest("li").classList.toggle('completed');
    });

}

function setRemoveListener(item) {
    
    item.addEventListener('click', function() {
        this.parentElement.remove();
    });

}

todoListItem.querySelectorAll('.checkbox').forEach(item => {
    setCheckboxListener(item);
});

todoListItem.querySelectorAll('.fa-trash-alt').forEach(item => {
    setRemoveListener(item);
});

