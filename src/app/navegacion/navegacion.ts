import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';

@Component({
  imports: [RouterLink, RouterLinkActive, RouterOutlet],
  selector: 'app-navegacion',
  styleUrl: './navegacion.css',
  templateUrl: './navegacion.html',
})
export class Navegacion {}
