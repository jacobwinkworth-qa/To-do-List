const params = new URLSearchParams(window.location.search);

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

        // data
        let task = {
            "name": newItem,
        }

        postData('http://localhost:8081/task/create/', task);
    }
});

todoListItem.querySelectorAll('.checkbox').forEach(item => {
    setCheckboxListener(item);
});

todoListItem.querySelectorAll('.fa-trash-alt').forEach(item => {
    setRemoveListener(item);
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
        deleteData('http://localhost:8081/task/delete/', 1)
    });

}

function postData(url, data) {

    fetch(url, {
        method: 'POST', // or 'PUT'
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
    .then(res => res.json())
    .then(data => {
        console.log('Success:', data);
    })
    .catch((error) => {
        console.error('Error:', error);
    });
}

function deleteData(url, id) {
    fetch(url + id, {
        method: 'DELETE'
    })
    .then(res => res.text())
    .then(res => console.log(res))
}
