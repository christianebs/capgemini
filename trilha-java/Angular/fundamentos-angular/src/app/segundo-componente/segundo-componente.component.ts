import { Component } from '@angular/core';

@Component({
  selector: 'app-segundo-componente',
  templateUrl: './segundo-componente.component.html',
  styleUrls: ['./segundo-componente.component.css']
})
export class SegundoComponenteComponent {
  nome = "Chris";
  dataNascimento = "1990-09-27";
  urlImagem = "/assets/avatar.png";

  mostrarDataNascimento() {
    alert(`A data de nascimento é: ${this.dataNascimento}`);
  }
}
