#include <stdio.h>
#include <unistd.h>

int main()
{
printf("\n");
int Gid = getpid();
int pid;

printf("I am the grandparent process G and my pid is %d.\n", Gid);

pid = fork();
wait();

if(pid == 0)
{
int Pid = getpid();

printf("I am the parent process P and my pid is %d. ", Pid);
printf("My parent G has pid %d.\n", Gid);
int cid = fork();
wait();

if(cid == 0)
{
int Cid = getpid();

printf("I am the child process C and my pid is %d. ", Cid);
printf("My parent P has pid %d. ", Pid);
printf("My grandparent G has pid %d.\n", Gid);

}
}
return 0;
}
