document.addEventListener("DOMContentLoaded", function() {
    const likeButtons = document.querySelectorAll(".like-button");
    likeButtons.forEach(button => {
        // Extract the initial state from the button's data attributes
        const isLiked = button.getAttribute("data-isliked") === "true";
        button.textContent = isLiked ? "Unlike" : "Like";

        button.addEventListener("click", function() {
            const postId = this.getAttribute("data-postid");
            const postDiv = document.getElementById("post-" + postId);
            const likeCountSpan = postDiv.querySelector(".like-count");
            
            const isCurrentlyLiked = this.textContent === "Unlike";
            const url = `like?postId=${postId}&isLiked=${!isCurrentlyLiked}`;

            fetch(url, {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                }
            }).then(response => response.json())
              .then(data => {
                  likeCountSpan.textContent = data.likeCount;
                  this.textContent = data.isLiked ? "Unlike" : "Like";
              });
        });
    });
});