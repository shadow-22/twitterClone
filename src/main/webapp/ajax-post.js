// Get references to the form and button elements
var postForm = document.getElementById("postForm");
var postButton = document.getElementById("postButton");

// Wrap your code in a DOMContentLoaded event handler
document.addEventListener("DOMContentLoaded", function() {
    // This code will be executed when the DOM is fully loaded

    // Add event listener to the "Post" button
    var postButton = document.getElementById("postButton");
    console.log("postButton:", postButton);
    postButton.addEventListener("click", createPost);
});


function createPost() {
    var postContent = document.getElementById("postContent").value;

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var newPost = JSON.parse(xhr.responseText);
                displayNewPost(newPost);
            } else {
                console.error("Error: " + xhr.statusText);
            }
        }
    };

    xhr.open("POST", "CreatePostServlet", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send("postContent=" + encodeURIComponent(postContent));
}

function displayNewPost(newPost) {
    var postsContainer = document.getElementById("postsContainer");

    var postDiv = document.createElement("div");
    postDiv.className = "post";
    
    var usernameParagraph = document.createElement("p");
    usernameParagraph.innerHTML = "<strong>" + newPost.username + "</strong>";
    postDiv.appendChild(usernameParagraph);

    var contentParagraph = document.createElement("p");
    contentParagraph.textContent = newPost.postContent;
    postDiv.appendChild(contentParagraph);

    postsContainer.prepend(postDiv); // Add the new post to the top of the posts container
}