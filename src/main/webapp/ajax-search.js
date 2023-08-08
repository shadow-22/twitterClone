function searchUsers(pageType) {
    var searchKeyword = document.getElementById("searchKeyword").value;
    var resultsElementId = pageType === 'secure' ? "searchResultsSecure" : "searchResultsIndex";

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var searchResults = JSON.parse(xhr.responseText);
                displaySearchResults(searchResults, resultsElementId);
            } else {
                console.error("Error: " + xhr.statusText);
            }
        }
    };

    xhr.open("POST", "search", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send("searchKeyword=" + encodeURIComponent(searchKeyword));
}

function displaySearchResults(searchResults, resultsElementId) {
    var searchResultsDiv = document.getElementById(resultsElementId);

    // Clear previous search results
    searchResultsDiv.innerHTML = "";

    if (searchResults.length === 0) {
        searchResultsDiv.innerHTML = "No results found.";
        return;
    }

    for (var i = 0; i < searchResults.length; i++) {
        var userLink = document.createElement("a");
        userLink.href = "secure-page.jsp?username=" + encodeURIComponent(searchResults[i].username);
        userLink.textContent = searchResults[i].username;

        var listItem = document.createElement("li");
        listItem.appendChild(userLink);

        searchResultsDiv.appendChild(listItem);
    }
}

// Get references to the form and button elements
var postForm = document.getElementById("postForm");
var postButton = document.getElementById("postButton");

// Wrap your code in a DOMContentLoaded event handler
document.addEventListener("DOMContentLoaded", function() {
    // This code will be executed when the DOM is fully loaded

    // Add event listener to the "Post" button
    var postButton = document.getElementById("postButton");
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

