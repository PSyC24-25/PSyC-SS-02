document.addEventListener("DOMContentLoaded", function () {
    const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');

    document.querySelectorAll("button[data-id]").forEach(button => {
        button.addEventListener("click", function () {
            let codPelicula = this.getAttribute("data-id");
            let csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');

            console.log(codPelicula);
            fetch(`/admin/peliculas/borrar/${codPelicula}`, {
                method: "DELETE",
                headers: {
                    "X-CSRF-TOKEN": csrfToken
                }
            }).then(response => {
                if (response.ok) {
                    setTimeout(() => window.location.reload(), 500);
                } else {
                    alert("No se ha podido eliminar la película");
                }
            });
        });
    });
});