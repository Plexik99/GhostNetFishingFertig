
const anonym = document.getElementById("anonym");
const kontaktdaten = document.getElementById("kontaktdaten");

function kontaktUmschalten() {
    kontaktdaten.disabled = anonym.checked;
    document.getElementById("melderName").required = !anonym.checked;
    document.getElementById("melderTelefon").required = !anonym.checked;
}

anonym.addEventListener("change", kontaktUmschalten);
kontaktUmschalten();
