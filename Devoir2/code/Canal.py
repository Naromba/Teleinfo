import random
import threading

class Canal:
    """
    Canal de transmission générique simulant un lien non fiable.
    Les paramètres probErreur, probPerte et delaiMax sont configurables
    pour reproduire différents scénarios.
    """

    def __init__(self, probErreur=0.0, probPerte=0.0, delaiMax=0):
        """
        Initialise le canal.
        probErreur : probabilité de corruption d'une trame (0.0 à 1.0)
        probPerte  : probabilité de perte d'une trame
        delaiMax   : délai maximal (en ms)
        """
        self.probErreur = probErreur
        self.probPerte = probPerte
        self.delaiMax = delaiMax
        self._fifo = []  # File FIFO pour garder l’ordre
        print(f"[Canal] Initialisé → erreur={probErreur}, perte={probPerte}, delaiMax={delaiMax} ms")

    def transmettre(self, trame, callback_reception):
        """
        Simule la transmission d'une trame ou d'un ACK via le canal.
        Le callback est appelé quand la trame arrive au récepteur.
        """
        tirage = random.random()

        #  Trame perdue
        if tirage < self.probPerte:
            print(f"[Canal]  Trame perdue : {trame}")
            return

        #  Trame corrompue
        if tirage < self.probPerte + self.probErreur:
            trame = self._corrompre(trame)
            print(f"[Canal] Trame corrompue : {trame}")

        # Délai simulé
        delai = random.randint(0, self.delaiMax) if self.delaiMax > 0 else 0
        self._fifo.append(trame)
        print(f"[Canal]  Transmission (+{delai} ms) : {trame}")
        threading.Timer(delai / 1000, self._livrer, args=(callback_reception,)).start()

    def _livrer(self, callback_reception):
        """Livre la trame la plus ancienne (ordre préservé)."""
        if not self._fifo:
            return
        trame = self._fifo.pop(0)
        print(f"[Canal]  Trame livrée : {trame}")
        callback_reception(trame)

    def _corrompre(self, trame):
        """Altère un caractère dans le champ données (si présent)."""
        if hasattr(trame, "donnees") and trame.donnees:
            pos = random.randint(0, len(trame.donnees) - 1)
            c = trame.donnees[pos]
            trame.donnees = trame.donnees[:pos] + chr((ord(c) + 1) % 256) + trame.donnees[pos+1:]
        return trame
