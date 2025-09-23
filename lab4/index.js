document.getElementById("fetch-user").addEventListener("click", function () {
  const userId = document.getElementById("user-id").value;

  fetch(
    `https://8fdff57f-1bfa-4d26-9a2b-c5eb19723954.mock.pstmn.io/api/user/${userId}`
  )
    .then((response) => response.json())
    .then((data) => {
      document.getElementById("user-name").innerText = "Name: " + data.name;
      document.getElementById("user-email").innerText = "Email: " + data.email;
      document.getElementById("user-info").style.display = "block";
    })
    .catch((error) => console.error("Error fetching user info:", error));
});
