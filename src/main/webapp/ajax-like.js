document.addEventListener("DOMContentLoaded", function() {
    const likeButtons = document.querySelectorAll(".like-button");
    likeButtons.forEach(button => {
        button.addEventListener("click", function() {
            const postId = this.getAttribute("data-postid");
            const postDiv = document.getElementById("post-" + postId);
            const likeCountSpan = postDiv.querySelector(".like-count");
            
            const isLiked = this.textContent === "Like" ? true : false;
            const url = `like?postId=${postId}&isLiked=${isLiked}`;

            fetch(url, {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                }
            }).then(response => response.json())
              .then(data => {
                  likeCountSpan.textContent = data.likeCount;
                  this.textContent = isLiked ? "Unlike" : "Like";
              });
        });
    });
});