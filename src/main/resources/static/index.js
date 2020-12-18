// base URL
const BASE_URL = "http://localhost:8081/";
// CRUD URLs
const CREATE_URL = BASE_URL + "list/create/";
const READ_URL = BASE_URL + "list/read/";
const PARTIAL_UPDATE_URL = BASE_URL + "list/patch/"
const DELETE_URL = BASE_URL + "list/delete/"

const params = new URLSearchParams(window.location.search);

var todoListForm = document.querySelector('.form');
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
        let redirect = BASE_URL + "view.html?id=" + data['id'];
        newListItem.innerHTML = "<div class='form-check'> <a href='"+redirect+"'>" + data['topic'] + "</a> </div> <i class='fas fa-edit'></i> <i class='fas fa-trash-alt'></i>";
        todoListItem.append(newListItem);

        setRemoveListener(newListItem.querySelector('.fa-trash-alt'));
        setEditListener(newListItem.querySelector('.fa-edit'));
    } )
}

// read
getData = (url) => {

    fetch(url)
    .then(response => response.json())
    .then(function(data) {
        for (let key in data) {
            let newListItem = document.createElement("li");
            let id = data[key]['id'];
            newListItem.setAttribute('id', id);
            let redirect = BASE_URL + "view.html?id=" + id;
            newListItem.innerHTML = "<div class='form-check'> <a href='"+redirect+"'>" + data[key]['topic'] + "</a> </div> <i class='fas fa-edit'></i> <i class='fas fa-trash-alt'></i>"
            todoListItem.append(newListItem);

            setRemoveListener(newListItem.querySelector('.fa-trash-alt'));
            setEditListener(newListItem.querySelector('.fa-edit'));
        };
    })

}

// (partial) update
partialUpdateData = (url, id, data) => {

    settings ={
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    }

    fetch(url + id, settings)
    .then(response => response.json())
    .then(data => console.log(data));

}

// delete
deleteData = (url, id) => {

    fetch(url + id, {
        method: 'DELETE'
    })
    .then(res => res.text())
    .then(res => console.log(res))

}

// item listeners
setEditListener = (item) => {

    item.addEventListener('click', function() {

        let topic = prompt("Please enter a new list name");

        data = {
            "topic": topic,
        }
        
        let id = this.parentElement.id;

        partialUpdateData(PARTIAL_UPDATE_URL, id, data);

        this.parentElement.querySelector('a').textContent = topic;
    });

}

setRemoveListener = (item) => {

    item.addEventListener('click', function() {
        let id = this.parentElement.id;
        this.parentElement.remove();
        deleteData(DELETE_URL, id)
    });

}

// create new list on load (only executes once, unless user clears local storage)
window.onload = function () {
    if (localStorage.getItem("newListCreated") === null) {
        postData(CREATE_URL, { "topic": "new list" });
        localStorage.setItem("newListCreated", true);
    }
}

// populate list
getData(READ_URL);

// button listener
todoListForm
.addEventListener("submit", function(event) {
    event.preventDefault();

    let newItem = todoListInput.value;  // post

    if (newItem) {

        // data
        const tdList = {
            "topic": newItem
        }
        
        postData(CREATE_URL, tdList);
        todoListInput.value = '';
    }
});