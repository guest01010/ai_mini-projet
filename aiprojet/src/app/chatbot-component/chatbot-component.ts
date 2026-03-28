import {ChangeDetectorRef, Component} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpClient, HttpDownloadProgressEvent, HttpEventType, HttpProgressEvent} from '@angular/common/http';
import {MarkdownComponent} from 'ngx-markdown';
import {count} from 'rxjs';

@Component({
  selector: 'app-chatbot-component',
  imports: [
    FormsModule,
    MarkdownComponent
  ],
  templateUrl: './chatbot-component.html',
  styleUrl: './chatbot-component.css',
})
export class ChatbotComponent {

  constructor(private http:HttpClient,private cd:ChangeDetectorRef) {
    this.initSpeechRecognition();
  }
  speking:boolean=false
  question:string="";
  response:any;
  p:boolean=false;
  reponsechat:boolean=true;



  isListening: boolean = false;
  private recognition: any | null = null;


  metretrue(){
    this.reponsechat=true;
  }
  metrefalse(){
    this.reponsechat=false;
  }

  askAgent(){
    this.p=true;
    this.http.get("http://localhost:9093/askAgent?message="+this.question
      ,{responseType:"text",observe:"events",reportProgress:true})
      .subscribe({
          next:resp=>{
            if(resp.type==HttpEventType.DownloadProgress){
              this.cd.detectChanges();
              this.response=(resp as HttpDownloadProgressEvent).partialText;

            }}
          ,error:err=>{
            console.log(err)
          },complete:()=>{
            this.p=false;
            this.speakResponse();
          }
        }
      );
  }
  uploadFile(event: Event) {
    this.p=true;
    const input =event.target as HTMLInputElement;
    const formdata=new FormData();
    if (input.files && input.files[0]){
      formdata.append('file',input.files[0],input.files[0].name);
      this.http.post("http://localhost:9093/loadfile",formdata).subscribe(
        {
          next:()=>{
            console.log("fichier charge avec succees")}
          ,error:err=>{
            console.error(err)
          },complete:()=>{
            this.p=false;
          }
        }
      )
    }
  }

  startListening() {
    if (!this.recognition) {
      return;
    }
    this.question = '';
    this.isListening = true;
    this.recognition.start();
  }

  stopListening() {
    if (!this.recognition) {
      return;
    }
    this.recognition.stop();
    this.isListening = false;
  }

  stopSpeaking() {
    this.speking=false;
    window.speechSynthesis.cancel();
  }

  private initSpeechRecognition() {
    const win = window as any;
    const SpeechRecognition = win.SpeechRecognition || win.webkitSpeechRecognition;
    if (!SpeechRecognition) {
      return;
    }

    this.recognition = new SpeechRecognition();
    this.recognition.lang = 'fr-FR';
    this.recognition.interimResults = true;
    this.recognition.continuous = false;

    this.recognition.onresult = (event: any) => {
      let transcript = '';
      for (let i = 0; i < event.results.length; i++) {
        transcript += event.results[i][0].transcript;
      }
      this.question = transcript.trim();
      this.cd.detectChanges();
    };

    this.recognition.onend = () => {
      this.isListening = false;
      this.cd.detectChanges();
    };
  }


  private speakResponse() {
    if(this.reponsechat){
    this.speking=true;
    if (!this.response?.trim()) {
      return;
    }
    window.speechSynthesis.cancel();
    const utterance = new SpeechSynthesisUtterance(this.response);
    utterance.lang = 'fr-FR';
    utterance.rate = 1;
    utterance.pitch = 1;
    window.speechSynthesis.speak(utterance);
  }}
}
