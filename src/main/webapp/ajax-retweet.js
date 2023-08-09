document.addEventListener("DOMContentLoaded", function() {
    // Add event listener to all Retweet buttons
    var retweetButtons = document.querySelectorAll(".retweetButton");
    retweetButtons.forEach(function(button) {
        button.addEventListener("click", function() {
            var postId = button.getAttribute("data-postid");
            retweetPost(button, postId);
        });
    });
});

function retweetPost(button, postId) {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                // Update UI to indicate successful retweet
                button.innerText = "Retweeted";
                button.disabled = true;
                
                // Assuming you have a postsContainer element to append retweets
                var postsContainer = document.getElementById("postsContainer");
                var newPostContent = "Retweeted: " + xhr.responseText; // Update with actual response data
                var newPostDiv = document.createElement("div");
                newPostDiv.className = "post";
                var contentParagraph = document.createElement("p");
                contentParagraph.textContent = newPostContent;
                newPostDiv.appendChild(contentParagraph);
                postsContainer.appendChild(newPostDiv);
            } else {
                console.error("Error: " + xhr.statusText);
            }
        }
    };

    xhr.open("POST", "RetweetServlet", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send("postId=" + encodeURIComponent(postId));
}
