## Break-Shop


배달 앱을 구현.

그 백엔드 파트를 단독으로 구현해보며, 그 과정과 트러블 슈팅 과정을 담는다.

---

## 중점사항 
- Docker-compose 를 통한 배포 환경 구성

- Redis를 통한 세션 인증방식 도입
    - 캐시, 세션, 카트 등 단위별 서버 분리

- 스프링에서의 단위 테스트 구현

## 개발인원 / 기간

1인 개발 / 2023-01-13 ~

## 프로젝트 구성 
---



## 어떻게 실행하나요?
--- 
```
cd breakshop

./gradlew build

docker build -t breakshop .

docker compose up -d
```

## 사용 기술
---
- JAVA 11
- SpringBoot
    - lettuce
    - modelmapper
    - 
- Redis
- Mysql
- Gradle
- SpringDataJPA
- Docker


## API 설명

### 앞서, 본 프로젝트는 자동 문서화되어있습니다.
```
hosturl:8090/swagger-ui/index.html
```
에서 확인 가능합니다.

---
<div class="markdown prose w-full break-words dark:prose-invert light"><h2>메뉴 옵션 관련 API</h2><h3>DELETE /stores/{storeId}/menus/{menuId}/options</h3><p>메뉴 옵션 리스트를 삭제합니다.</p><p><strong>Parameters</strong></p><table><thead><tr><th>Name</th><th>Description</th><th>Type</th><th>In</th><th>Required</th></tr></thead><tbody><tr><td>storeId</td><td>가게 ID</td><td>integer($int64)</td><td>path</td><td>Yes</td></tr><tr><td>menuId</td><td>메뉴 ID</td><td>integer($int64)</td><td>path</td><td>Yes</td></tr></tbody></table><p><strong>Responses</strong></p><table><thead><tr><th>Code</th><th>Description</th></tr></thead><tbody><tr><td>200</td><td>OK</td></tr></tbody></table><h3>GET /stores/{storeId}/menus/{menuId}/options</h3><p>메뉴 옵션 리스트를 조회합니다.</p><p><strong>Parameters</strong></p><table><thead><tr><th>Name</th><th>Description</th><th>Type</th><th>In</th><th>Required</th></tr></thead><tbody><tr><td>storeId</td><td>가게 ID</td><td>integer($int64)</td><td>path</td><td>Yes</td></tr><tr><td>menuId</td><td>메뉴 ID</td><td>integer($int64)</td><td>path</td><td>Yes</td></tr></tbody></table><p><strong>Responses</strong></p><table><thead><tr><th>Code</th><th>Description</th></tr></thead><tbody><tr><td>200</td><td>OK</td></tr></tbody></table><p><strong>Response Body Example</strong></p><pre><div class="bg-black rounded-md mb-4"><div class="flex items-center relative text-gray-200 bg-gray-800 px-4 py-2 text-xs font-sans justify-between rounded-t-md"><span>json</span><button class="flex ml-auto gap-2"><svg stroke="currentColor" fill="none" stroke-width="2" viewBox="0 0 24 24" stroke-linecap="round" stroke-linejoin="round" class="h-4 w-4" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"><path d="M16 4h2a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2h2"></path><rect x="8" y="2" width="8" height="4" rx="1" ry="1"></rect></svg>Copy code</button></div><div class="p-4 overflow-y-auto"><code class="!whitespace-pre hljs language-json"><span class="hljs-punctuation">[</span>
  <span class="hljs-punctuation">{</span>
    <span class="hljs-attr">"id"</span><span class="hljs-punctuation">:</span> <span class="hljs-number">0</span><span class="hljs-punctuation">,</span>
    <span class="hljs-attr">"name"</span><span class="hljs-punctuation">:</span> <span class="hljs-string">"string"</span><span class="hljs-punctuation">,</span>
    <span class="hljs-attr">"price"</span><span class="hljs-punctuation">:</span> <span class="hljs-number">0</span><span class="hljs-punctuation">,</span>
    <span class="hljs-attr">"menuId"</span><span class="hljs-punctuation">:</span> <span class="hljs-number">0</span>
  <span class="hljs-punctuation">}</span>
<span class="hljs-punctuation">]</span>
</code></div></div></pre><h3>POST /stores/{storeId}/menus/{menuId}/options</h3><p>메뉴 옵션 리스트를 등록합니다.</p><p><strong>Parameters</strong></p><table><thead><tr><th>Name</th><th>Description</th><th>Type</th><th>In</th><th>Required</th></tr></thead><tbody><tr><td>storeId</td><td>가게 ID</td><td>integer($int64)</td><td>path</td><td>Yes</td></tr><tr><td>menuId</td><td>메뉴 ID</td><td>integer($int64)</td><td>path</td><td>Yes</td></tr></tbody></table><p><strong>Request Body Example</strong></p><pre><div class="bg-black rounded-md mb-4"><div class="flex items-center relative text-gray-200 bg-gray-800 px-4 py-2 text-xs font-sans justify-between rounded-t-md"><span>json</span><button class="flex ml-auto gap-2"><svg stroke="currentColor" fill="none" stroke-width="2" viewBox="0 0 24 24" stroke-linecap="round" stroke-linejoin="round" class="h-4 w-4" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"><path d="M16 4h2a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2h2"></path><rect x="8" y="2" width="8" height="4" rx="1" ry="1"></rect></svg>Copy code</button></div><div class="p-4 overflow-y-auto"><code class="!whitespace-pre hljs language-json"><span class="hljs-punctuation">{</span>
  <span class="hljs-attr">"optionList"</span><span class="hljs-punctuation">:</span> <span class="hljs-punctuation">[</span>
    <span class="hljs-punctuation">{</span>
      <span class="hljs-attr">"id"</span><span class="hljs-punctuation">:</span> <span class="hljs-number">0</span><span class="hljs-punctuation">,</span>
      <span class="hljs-attr">"name"</span><span class="hljs-punctuation">:</span> <span class="hljs-string">"string"</span><span class="hljs-punctuation">,</span>
      <span class="hljs-attr">"price"</span><span class="hljs-punctuation">:</span> <span class="hljs-number">0</span><span class="hljs-punctuation">,</span>
      <span class="hljs-attr">"menuId"</span><span class="hljs-punctuation">:</span> <span class="hljs-number">0</span>
    <span class="hljs-punctuation">}</span>
  <span class="hljs-punctuation">]</span><span class="hljs-punctuation">,</span>
  <span class="hljs-attr">"ownerEmail"</span><span class="hljs-punctuation">:</span> <span class="hljs-string">"string"</span>
<span class="hljs-punctuation">}</span>
</code></div></div></pre><p><strong>Responses</strong></p><table><thead><tr><th>Code</th><th>Description</th></tr></thead><tbody><tr><td>200</td><td>OK</td></tr></tbody></table></div>
<div class="markdown prose w-full break-words dark:prose-invert light">


<h2>가게 관련 API 요청과 응답</h2><h3>가게 생성</h3><table><thead><tr><th>HTTP 메서드</th><th>API 경로</th><th>요청 파라미터</th><th>요청 바디</th><th>응답</th></tr></thead><tbody><tr><td>POST</td><td>/stores</td><td>query: store(object)</td><td>application/json: string</td><td>200 OK</td></tr></tbody></table><p><strong>요청 바디 (application/json)</strong></p><pre><div class="bg-black rounded-md mb-4"><div class="flex items-center relative text-gray-200 bg-gray-800 px-4 py-2 text-xs font-sans justify-between rounded-t-md"><span>json</span><button class="flex ml-auto gap-2"><svg stroke="currentColor" fill="none" stroke-width="2" viewBox="0 0 24 24" stroke-linecap="round" stroke-linejoin="round" class="h-4 w-4" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"><path d="M16 4h2a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2h2"></path><rect x="8" y="2" width="8" height="4" rx="1" ry="1"></rect></svg>Copy code</button></div><div class="p-4 overflow-y-auto"><code class="!whitespace-pre hljs language-json"><span class="hljs-punctuation">{</span>
  <span class="hljs-attr">"name"</span><span class="hljs-punctuation">:</span> <span class="hljs-string">"string"</span><span class="hljs-punctuation">,</span>
  <span class="hljs-attr">"phone"</span><span class="hljs-punctuation">:</span> <span class="hljs-string">"string"</span><span class="hljs-punctuation">,</span>
  <span class="hljs-attr">"address"</span><span class="hljs-punctuation">:</span> <span class="hljs-string">"string"</span><span class="hljs-punctuation">,</span>
  <span class="hljs-attr">"ownerId"</span><span class="hljs-punctuation">:</span> <span class="hljs-number">0</span><span class="hljs-punctuation">,</span>
  <span class="hljs-attr">"introduction"</span><span class="hljs-punctuation">:</span> <span class="hljs-string">"string"</span><span class="hljs-punctuation">,</span>
  <span class="hljs-attr">"categoryName"</span><span class="hljs-punctuation">:</span> <span class="hljs-string">"string"</span>
<span class="hljs-punctuation">}</span>
</code></div></div></pre><h2>주문 승인</h2>
<table><thead><tr><th>HTTP 메서드</th><th>API 경로</th><th>요청 파라미터</th><th>요청 바디</th><th>응답</th></tr></thead><tbody><tr><td>POST</td><td>/stores/{storeId}/orders/{orderId}/approve</td><td>path: orderId, storeId</td><td>application/json: string</td><td>200 OK</td></tr></tbody></table><p><strong>요청 바디 (application/json)</strong></p><pre><div class="bg-black rounded-md mb-4"><div class="flex items-center relative text-gray-200 bg-gray-800 px-4 py-2 text-xs font-sans justify-between rounded-t-md"><span>json</span><button class="flex ml-auto gap-2"><svg stroke="currentColor" fill="none" stroke-width="2" viewBox="0 0 24 24" stroke-linecap="round" stroke-linejoin="round" class="h-4 w-4" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"><path d="M16 4h2a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2h2"></path><rect x="8" y="2" width="8" height="4" rx="1" ry="1"></rect></svg>Copy code</button></div><div class="p-4 overflow-y-auto"><code class="!whitespace-pre hljs language-json"><span class="hljs-string">"string"</span>
</code></div></div></pre><h3>메뉴 추가</h3><table><thead><tr><th>HTTP 메서드</th><th>API 경로</th><th>요청 파라미터</th><th>요청 바디</th><th>응답</th></tr></thead><tbody><tr><td>POST</td><td>/stores/{storeId}/menus</td><td>path: storeId</td><td>application/json: object</td><td>200 OK</td></tr></tbody></table><p><strong>요청 바디 (application/json)</strong></p><pre><div class="bg-black rounded-md mb-4"><div class="flex items-center relative text-gray-200 bg-gray-800 px-4 py-2 text-xs font-sans justify-between rounded-t-md"><span>json</span><button class="flex ml-auto gap-2"><svg stroke="currentColor" fill="none" stroke-width="2" viewBox="0 0 24 24" stroke-linecap="round" stroke-linejoin="round" class="h-4 w-4" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"><path d="M16 4h2a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2h2"></path><rect x="8" y="2" width="8" height="4" rx="1" ry="1"></rect></svg>Copy code</button></div><div class="p-4 overflow-y-auto"><code class="!whitespace-pre hljs language-json"><span class="hljs-punctuation">{</span>
  <span class="hljs-attr">"menu"</span><span class="hljs-punctuation">:</span> <span class="hljs-punctuation">{</span>
    <span class="hljs-attr">"id"</span><span class="hljs-punctuation">:</span> <span class="hljs-number">0</span><span class="hljs-punctuation">,</span>
    <span class="hljs-attr">"name"</span><span class="hljs-punctuation">:</span> <span class="hljs-string">"string"</span><span class="hljs-punctuation">,</span>
    <span class="hljs-attr">"price"</span><span class="hljs-punctuation">:</span> <span class="hljs-number">0</span><span class="hljs-punctuation">,</span>
    <span class="hljs-attr">"photo"</span><span class="hljs-punctuation">:</span> <span class="hljs-string">"string"</span><span class="hljs-punctuation">,</span>
    <span class="hljs-attr">"description"</span><span class="hljs-punctuation">:</span> <span class="hljs-string">"string"</span><span class="hljs-punctuation">,</span>
    <span class="hljs-attr">"storeId"</span><span class="hljs-punctuation">:</span> <span class="hljs-number">0</span>
  <span class="hljs-punctuation">}</span><span class="hljs-punctuation">,</span>
  <span class="hljs-attr">"ownerEmail"</span><span class="hljs-punctuation">:</span> <span class="hljs-string">"string"</span>
<span class="hljs-punctuation">}</span>
</code></div></div></pre></div>
---

<div class="relative flex w-[calc(100%-50px)] flex-col gap-1 md:gap-3 lg:w-[calc(100%-115px)]"><div class="flex flex-grow flex-col gap-3"><div class="min-h-[20px] flex flex-col items-start gap-4 whitespace-pre-wrap"><div class="markdown prose w-full break-words dark:prose-invert light"><table><thead><tr><th>HTTP 메소드</th><th>URI</th><th>설명</th></tr></thead><tbody><tr><td>DELETE</td><td>/users/{userId}/carts</td><td>유저의 카트 리스트 삭제</td></tr><tr><td>GET</td><td>/users/{userId}/carts</td><td>유저의 카트 리스트 로딩</td></tr><tr><td>POST</td><td>/users/{userId}/carts</td><td>유저의 메뉴를 카트에 추가</td></tr></tbody></table><h3>DELETE /users/{userId}/carts</h3><ul><li>유저의 카트 리스트를 삭제합니다.</li></ul><h3>GET /users/{userId}/carts</h3><ul><li>유저의 카트 리스트를 조회합니다.</li></ul><h3>POST /users/{userId}/carts</h3><ul><li>유저의 메뉴를 카트에 추가합니다.</li><li>Request Body:<pre><div class="bg-black rounded-md mb-4"><div class="flex items-center relative text-gray-200 bg-gray-800 px-4 py-2 text-xs font-sans justify-between rounded-t-md"><span>json</span><button class="flex ml-auto gap-2"><svg stroke="currentColor" fill="none" stroke-width="2" viewBox="0 0 24 24" stroke-linecap="round" stroke-linejoin="round" class="h-4 w-4" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"><path d="M16 4h2a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2H6a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2h2"></path><rect x="8" y="2" width="8" height="4" rx="1" ry="1"></rect></svg>Copy code</button></div><div class="p-4 overflow-y-auto"><code class="!whitespace-pre hljs language-json"><span class="hljs-punctuation">{</span>
  <span class="hljs-attr">"name"</span><span class="hljs-punctuation">:</span> <span class="hljs-string">"string"</span><span class="hljs-punctuation">,</span>
  <span class="hljs-attr">"price"</span><span class="hljs-punctuation">:</span> <span class="hljs-number">0</span><span class="hljs-punctuation">,</span>
  <span class="hljs-attr">"menuId"</span><span class="hljs-punctuation">:</span> <span class="hljs-number">0</span><span class="hljs-punctuation">,</span>
  <span class="hljs-attr">"storeId"</span><span class="hljs-punctuation">:</span> <span class="hljs-number">0</span><span class="hljs-punctuation">,</span>
  <span class="hljs-attr">"count"</span><span class="hljs-punctuation">:</span> <span class="hljs-number">0</span><span class="hljs-punctuation">,</span>
  <span class="hljs-attr">"optionList"</span><span class="hljs-punctuation">:</span> <span class="hljs-punctuation">[</span>
    <span class="hljs-punctuation">{</span>
      <span class="hljs-attr">"optionId"</span><span class="hljs-punctuation">:</span> <span class="hljs-number">0</span><span class="hljs-punctuation">,</span>
      <span class="hljs-attr">"name"</span><span class="hljs-punctuation">:</span> <span class="hljs-string">"string"</span><span class="hljs-punctuation">,</span>
      <span class="hljs-attr">"price"</span><span class="hljs-punctuation">:</span> <span class="hljs-number">0</span>
    <span class="hljs-punctuation">}</span>
  <span class="hljs-punctuation">]</span>
<span class="hljs-punctuation">}</span>
</code></div></div></pre></li></ul></div></div></div><div class="flex justify-between lg:block"><div class="text-gray-400 flex self-end lg:self-center justify-center mt-2 gap-2 md:gap-3 lg:gap-1 lg:absolute lg:top-0 lg:translate-x-full lg:right-0 lg:mt-0 lg:pl-2 visible"><button class="p-1 rounded-md hover:bg-gray-100 hover:text-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-gray-200 disabled:dark:hover:text-gray-400"><svg stroke="currentColor" fill="none" stroke-width="2" viewBox="0 0 24 24" stroke-linecap="round" stroke-linejoin="round" class="h-4 w-4" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"><path d="M14 9V5a3 3 0 0 0-3-3l-4 9v11h11.28a2 2 0 0 0 2-1.7l1.38-9a2 2 0 0 0-2-2.3zM7 22H4a2 2 0 0 1-2-2v-7a2 2 0 0 1 2-2h3"></path></svg></button><button class="p-1 rounded-md hover:bg-gray-100 hover:text-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-gray-200 disabled:dark:hover:text-gray-400"><svg stroke="currentColor" fill="none" stroke-width="2" viewBox="0 0 24 24" stroke-linecap="round" stroke-linejoin="round" class="h-4 w-4" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"><path d="M10 15v4a3 3 0 0 0 3 3l4-9V2H5.72a2 2 0 0 0-2 1.7l-1.38 9a2 2 0 0 0 2 2.3zm7-13h2.67A2.31 2.31 0 0 1 22 4v7a2.31 2.31 0 0 1-2.33 2H17"></path></svg></button></div></div></div>

<div class="relative flex w-[calc(100%-50px)] flex-col gap-1 md:gap-3 lg:w-[calc(100%-115px)]"><div class="flex flex-grow flex-col gap-3"><div class="min-h-[20px] flex flex-col items-start gap-4 whitespace-pre-wrap"><div class="markdown prose w-full break-words dark:prose-invert light"><table><thead><tr><th>API 이름</th><th>HTTP 메서드</th><th>경로</th><th>요청</th><th>응답</th><th>설명</th></tr></thead><tbody><tr><td>유저 존재 여부 확인</td><td>GET</td><td>/user/{id}/exists</td><td></td><td>200 OK</td><td>해당 id의 유저가 존재하는지 확인하는 API</td></tr><tr><td>로그아웃</td><td>GET</td><td>/user/logout</td><td></td><td>200 OK</td><td>유저 로그아웃</td></tr><tr><td>회원가입</td><td>POST</td><td>/user/signup</td><td>{"email": "<a href="mailto:string@string.com" target="_new">string@string.com</a>", "password": "string", "name": "string", "phone": "string", "address": "string", "level": "ROLE_OWNER"}</td><td>200 OK</td><td>유저 회원가입</td></tr><tr><td>로그인</td><td>POST</td><td>/user/login</td><td>{"email": "<a href="mailto:string@string.com" target="_new">string@string.com</a>", "password": "string"}</td><td>200 OK</td><td>유저 로그인</td></tr></tbody></table><p></p></div></div></div><div class="flex justify-between lg:block"><div class="text-gray-400 flex self-end lg:self-center justify-center mt-2 gap-2 md:gap-3 lg:gap-1 lg:absolute lg:top-0 lg:translate-x-full lg:right-0 lg:mt-0 lg:pl-2 visible"><button class="p-1 rounded-md hover:bg-gray-100 hover:text-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-gray-200 disabled:dark:hover:text-gray-400"><svg stroke="currentColor" fill="none" stroke-width="2" viewBox="0 0 24 24" stroke-linecap="round" stroke-linejoin="round" class="h-4 w-4" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"><path d="M14 9V5a3 3 0 0 0-3-3l-4 9v11h11.28a2 2 0 0 0 2-1.7l1.38-9a2 2 0 0 0-2-2.3zM7 22H4a2 2 0 0 1-2-2v-7a2 2 0 0 1 2-2h3"></path></svg></button><button class="p-1 rounded-md hover:bg-gray-100 hover:text-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-gray-200 disabled:dark:hover:text-gray-400"><svg stroke="currentColor" fill="none" stroke-width="2" viewBox="0 0 24 24" stroke-linecap="round" stroke-linejoin="round" class="h-4 w-4" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"><path d="M10 15v4a3 3 0 0 0 3 3l4-9V2H5.72a2 2 0 0 0-2 1.7l-1.38 9a2 2 0 0 0 2 2.3zm7-13h2.67A2.31 2.31 0 0 1 22 4v7a2.31 2.31 0 0 1-2.33 2H17"></path></svg></button></div></div></div>

<div class="relative flex w-[calc(100%-50px)] flex-col gap-1 md:gap-3 lg:w-[calc(100%-115px)]"><div class="flex flex-grow flex-col gap-3"><div class="min-h-[20px] flex flex-col items-start gap-4 whitespace-pre-wrap"><div class="markdown prose w-full break-words dark:prose-invert light"><h2>회원 탈퇴</h2><h3>DELETE /my-profiles</h3><p>회원을 탈퇴시키고, 세션에서 삭제합니다.</p><tbody><tr><td>200</td><td>OK</td></tr></tbody></table><hr><h2>비밀번호 변경</h2><h3>PATCH /my-profiles/password</h3></p><p>유저의 비밀번호를 변경합니다.</p><h4>Request Parameters</h4><table><thead><tr><th>Name</th><th>Type</th><th>Description</th><th>Required</th></tr></thead><tbody><tr><td>password</td><td>string</td><td>유저의 새로운 비밀번호</td><td>Required</td></tr></tbody></table></p></div></div></div><div class="flex justify-between lg:block"><div class="text-gray-400 flex self-end lg:self-center justify-center mt-2 gap-2 md:gap-3 lg:gap-1 lg:absolute lg:top-0 lg:translate-x-full lg:right-0 lg:mt-0 lg:pl-2 visible"><button class="p-1 rounded-md hover:bg-gray-100 hover:text-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-gray-200 disabled:dark:hover:text-gray-400"><svg stroke="currentColor" fill="none" stroke-width="2" viewBox="0 0 24 24" stroke-linecap="round" stroke-linejoin="round" class="h-4 w-4" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"><path d="M14 9V5a3 3 0 0 0-3-3l-4 9v11h11.28a2 2 0 0 0 2-1.7l1.38-9a2 2 0 0 0-2-2.3zM7 22H4a2 2 0 0 1-2-2v-7a2 2 0 0 1 2-2h3"></path></svg></button><button class="p-1 rounded-md hover:bg-gray-100 hover:text-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-gray-200 disabled:dark:hover:text-gray-400"><svg stroke="currentColor" fill="none" stroke-width="2" viewBox="0 0 24 24" stroke-linecap="round" stroke-linejoin="round" class="h-4 w-4" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg"><path d="M10 15v4a3 3 0 0 0 3 3l4-9V2H5.72a2 2 0 0 0-2 1.7l-1.38 9a2 2 0 0 0 2 2.3zm7-13h2.67A2.31 2.31 0 0 1 22 4v7a2.31 2.31 0 0 1-2.33 2H17"></path></svg></button></div></div></div>

