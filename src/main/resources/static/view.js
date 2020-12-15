const params = new URLSearchParams(window.location.search);

var todoListItem = document.querySelector('.todo-list');
var todoListInput = document.querySelector('.todo-list-input');


postData = (url, data) => {

    const settings = {
        method: 'POST', // or 'PUT'
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    };

    fetch(url, settings)
    .then(res => res.json())
    .then(function(data) {

        let newListItem = document.createElement("li");
        newListItem.innerHTML = "<div class='form-check'><label class='form-check-label'><input class='checkbox' type='checkbox' />"
        + data["name"] + "<i class='input-helper'></i></label></div> <i class='fas fa-edit'></i> <i class='fas fa-trash-alt'></i>";
        todoListItem.append(newListItem);
        todoListInput.value = ("");

    })
}

getData = (url) => {

    fetch(url)
    .then(response => response.json())
    .then(function(data) {
        for (let key in data) {
            console.log(key, data[key]);
            let newListItem = document.createElement("li");
            newListItem.innerHTML = "<div class='form-check'> List " + data[key]['id'] + " <i class='input-helper'></i></label></div> <i class='fas fa-edit'></i> <i class='fas fa-trash-alt'></i>"
            todoListItem.append(newListItem);
        };
    })

}

deleteData = (url) => {

    fetch(url + id, {
        method: 'DELETE'
    })
    .then(res => res.text())
    .then(res => console.log(res))

}

setCheckboxListener = (item) => {

    item.addEventListener('change', function () {
        if (this.getAttribute('checked')) {
            this.removeAttribute('checked');
        } else {
            this.setAttribute('checked', 'checked');
        }
        this.closest("li").classList.toggle('completed');
    });

}

setRemoveListener = (item) => {

    item.addEventListener('click', function() {
        this.parentElement.remove();
        deleteData('http://localhost:8081/task/delete/', 1)
    });

}

document
.querySelector('.todo-list-add-btn')
.addEventListener("click", function(event) {
    event.preventDefault();

    let newItem = todoListInput.value;

    if (newItem) {

        // data
        const task = {
            "name": newItem,
            "tdList": {
                "id": params.get('id')
            }
        }

        postData('http://localhost:8081/task/create/', task);

        setCheckboxListener(newListItem.querySelector('.checkbox'));
        setRemoveListener(newListItem.querySelector('.fa-trash-alt'));
    }
});

todoListItem.querySelectorAll('.checkbox').forEach(item => {
    setCheckboxListener(item);
});

todoListItem.querySelectorAll('.fa-trash-alt').forEach(item => {
    setRemoveListener(item);
});