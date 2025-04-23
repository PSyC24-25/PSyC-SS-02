document.addEventListener("DOMContentLoaded", function () {
    const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');

    //BORRAR
    document.querySelectorAll("button[data-id]").forEach(button => {
        button.addEventListener("click", function () {
            let codigo = this.getAttribute("data-id");
            const claseOculta = document.getElementById("clase-oculta");
            let clase = claseOculta.getAttribute("nom-clase");
            console.log(clase);
            let csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');

            fetch(`/admin/${clase}/borrar/${codigo}`, {
                method: "DELETE",
                headers: {
                    "X-CSRF-TOKEN": csrfToken
                }
            }).then(response => {
                if (response.ok) {
                    setTimeout(() => window.location.reload(), 500);
                } else {
                    setTimeout(() => window.location.reload(), 500);
                }
            });
        });
    });

});