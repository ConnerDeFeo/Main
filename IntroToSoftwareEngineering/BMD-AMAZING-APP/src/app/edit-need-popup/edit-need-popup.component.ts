import { Component, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { needType } from '../need';

@Component({
  selector: 'app-edit-need-popup',
  templateUrl: './edit-need-popup.component.html',
  styleUrl: './edit-need-popup.component.css'
})
export class EditNeedPopupComponent {
  
  modelForm: FormGroup;
  needType = needType;
  disabled = false;

  constructor(
    private formBuilder: FormBuilder,
    private dialogRef: MatDialogRef<EditNeedPopupComponent>,
    @Inject (MAT_DIALOG_DATA) public data: any,
  ){
    this.disabled = data[1] !== "admin";
    this.modelForm = this.formBuilder.group({
      name: [{value:data[0].name, disabled:this.disabled}, Validators.required],
      cost: [{value:data[0].cost,disabled:this.disabled}, Validators.required],
      quantity: [{value:data[0].quantity,disabled:this.disabled}, Validators.required],
      type: [{value:data[0].type,disabled:this.disabled}, Validators.required],
      id: [{value:data[0].id, disabled:this.disabled}]
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