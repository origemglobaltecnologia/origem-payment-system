import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { PaymentService } from '../../services/payment.service';

@Component({
  selector: 'app-payment-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './payment-list.html',
  styleUrl: './payment-list.css'
})
export class PaymentList implements OnInit {
  // Injetando o serviço que criamos anteriormente
  protected service = inject(PaymentService);

  ngOnInit() {
    // Dispara a busca de dados assim que o componente inicia
    this.service.listarTodos().subscribe({
      next: (dados) => console.log('Dados carregados:', dados),
      error: (err) => console.error('Erro ao buscar pagamentos:', err)
    });
  }
}
