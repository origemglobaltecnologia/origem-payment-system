import { Component } from '@angular/core';
import { RouterModule } from '@angular/router'; // Adicione este import

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterModule], // Adicione aqui
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {}
