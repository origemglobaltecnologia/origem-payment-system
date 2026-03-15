import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-payment-list',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './payment-list.html',
  styleUrl: './payment-list.css'
})
export class PaymentList {}
