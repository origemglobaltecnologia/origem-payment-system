import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { PaymentService } from '../../services/payment.service';
import { Payment } from '../../models/payment.model';

@Component({
  selector: 'app-payment-create',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './payment-create.html',
  styleUrl: './payment-create.css'
})
export class PaymentCreate {
  private service = inject(PaymentService);
  private router = inject(Router);

  // Objeto que será vinculado ao formulário
  novoPagamento: Payment = {
    descricao: '',
    valor: 0,
    metodo: 'PIX'
  };

  onSubmit() {
    this.service.salvar(this.novoPagamento).subscribe({
      next: () => {
        alert('Pagamento criado com sucesso!');
        this.router.navigate(['/payments']); // Volta para a lista
      },
      error: (err) => console.error('Erro ao salvar:', err)
    });
  }
}
