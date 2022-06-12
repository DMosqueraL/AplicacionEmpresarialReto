import {Component, OnInit} from '@angular/core';
import {ServiceService} from 'src/app/Service/service.service';
import {FormControl} from '@angular/forms';
import {Router} from '@angular/router';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.sass'],
  providers: [ServiceService]
})
export class ForgotPasswordComponent implements OnInit {
  userEmail = new FormControl('');
  constructor(
    private authService: ServiceService,
    private route: Router) { }

  ngOnInit(): void {
  }

  async onRecovery(){
    try {
      await this.authService.resetPassword(this.userEmail.value);
      window.alert('por favor revisa la bandeja de tu email para recuperar tus credenciales')
      this.route.navigate(['preguntas']);
    } catch (error) {
      console.log(error)
    }
  }

}
