import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { needType } from '../need';

@Component({
  selector: 'app-create-need-popup',
  templateUrl: './create-need-popup.component.html',
  styleUrl: './create-need-popup.component.css'
})
export class CreateNeedPopupComponent {
  
  modelForm: FormGroup;
  needType = needType;

  constructor(
    private formBuilder: FormBuilder,
    private dialogRef: MatDialogRef<CreateNeedPopupComponent>,
    @Inject (MAT_DIALOG_DATA) public data: any
  ){
    this.modelForm = this.formBuilder.group({
      name: ['', Validators.required],
      cost: [0, Validators.required],
      quantity: [0, Validators.required],
      type: [needType.Monetary, Validators.required]
    });
  }

  onSubmit(){
    if(this.modelForm.valid){
      this.dialogRef.close(this.modelForm.value);
    }
  }

  onCancel(){
    this.dialogRef.close()
  }
}
