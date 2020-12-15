// CRUD URLs
const CREATE_URL = "http://localhost:8081/list/create/";
const READ_URL = "http://localhost:8081/list/read";
// const UPDATE_URL
const DELETE_URL = "http://localhost:8081/list/delete/"

const params = new URLSearchParams(window.location.search);

var todoListItem = document.querySelector('.todo-list');
var todoListInput = document.querySelector('.todo-list-input');

// create
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
        newListItem.setAttribute('id', data['id']);
        newListItem.innerHTML = "<div class='form-check'> " + data['topic'] + " <i class='input-helper'></i></label></div> <i class='fas fa-edit'></i> <i class='fas fa-trash-alt'></i>"
        todoListItem.append(newListItem);
        todoListInput.value = ("");
        setRemoveListener(newListItem.querySelector('.fa-trash-alt'));

    } )
}

// read
getData = (url) => {

    fetch(url)
    .then(response => response.json())
    .then(function(data) {
        for (let key in data) {
            let newListItem = document.createElement("li");
            newListItem.innerHTML = "<div class='form-check'> " + data[key]['topic'] + " <i class='input-helper'></i></label></div> <i class='fas fa-edit'></i> <i class='fas fa-trash-alt'></i>"
            todoListItem.append(newListItem);
        };
    })

}

// update

// delete
deleteData = (url, id) => {

    fetch(url + id, {
        method: 'DELETE'
    })
    .then(res => res.text())
    .then(res => console.log(res))

}

setRemoveListener = (item) => {

    item.addEventListener('click', function() {
        let id = this.parentElement.id;
        this.parentElement.remove();
        deleteData(DELETE_URL, id)
    });

}

// populate list
getData(READ_URL);

// set listeners
document
.querySelector('.todo-list-add-btn')
.addEventListener("click", function(event) {
    event.preventDefault();

    let newItem = todoListInput.value;  // post

    if (newItem) {

        // data
        const tdList = {
            "topic": newItem
        }
        
        postData(CREATE_URL, tdList);

    }
});

todoListItem.querySelectorAll('.fa-trash-alt').forEach(item => {
    setRemoveListener(item);
});